package threadpool;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Created by Anur IjuoKaruKas on 2019/7/16
 */
public class ThreadPoolExecutor {

    private final HashSet<Worker> workers = new HashSet<>();

    private volatile ThreadFactory threadFactory;

    private final BlockingQueue<Runnable> workQueue;

    private final int corePoolSize;

    private final int maximumPoolSize;

    private volatile long keepAliveTime;

    private volatile TimeUnit unit;

    private final BiConsumer<Runnable, ThreadPoolExecutor> abortPolicy;

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
        BiConsumer<Runnable, ThreadPoolExecutor> abortPolicy) {
        this.threadFactory = threadFactory;
        this.workQueue = workQueue;
        this.maximumPoolSize = maximumPoolSize;
        this.corePoolSize = corePoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.abortPolicy = abortPolicy;
    }

    /**
     * 优先创建核心线程，核心线程满了以后，则优先将任务放入队列
     *
     * 队列满了以后，则启用非核心线程池，以防任务堆积
     *
     * 如果非核心线程池创建失败，则拒绝这个任务
     */
    public void execute(Runnable command) {
        if (getPoolSize() < corePoolSize) {
            if (addWorker(command, true)) {
                return;
            }
        }

        if (!workQueue.offer(command)) {
            if (!addWorker(command, false)) {
                reject(command);
            }
        }
    }

    /**
     * 新建一个 worker 线程、启动并纳入 workers
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        int ws = workers.size();
        if (ws >= (core ? corePoolSize : maximumPoolSize)) {
            return false;
        }

        Worker w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            workers.add(w);
            t.start();
        }
        return true;
    }

    /**
     * worker 线程池不断从 workQueue 中拉取 task 进行消费
     */
    private void runWorker(Worker w) {
        Runnable task = w.firstTask;
        w.firstTask = null;
        while (task != null || (task = getTask()) != null) {
            task.run();
        }

        processWorkerExit(w);
    }

    /**
     * 当线程执行完毕之前，将其从 workers 中移除
     */
    private void processWorkerExit(Worker w) {
        workers.remove(w);
    }

    /**
     * 当 runWorker 一定时间内获取不到任务时，就会 processWorkerExit 销毁
     */
    private Runnable getTask() {
        boolean timedOut = false;
        while (true) {
            try {
                if (timedOut) {
                    return null;
                }

                Runnable r = workQueue.poll(keepAliveTime, unit);
                if (r != null) {
                    return r;
                } else {
                    timedOut = true;
                }
            } catch (InterruptedException e) {
                timedOut = false;
            }
        }
    }

    private void reject(Runnable command) {
        abortPolicy.accept(command, this);
    }

    public int getPoolSize() {
        return workers.size();
    }

    private final class Worker implements Runnable {

        final Thread thread;

        Runnable firstTask;

        Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = threadFactory.newThread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final Runnable r = () -> {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5), new ThreadFactoryBuilder().build(),
            (runnable, threadPoolExecutor) -> {
                // 仿照 DiscardPolicy
                // Does nothing, which has the effect of discarding task r.
                System.out.println("有任务被丢弃了");
            });

        // 提交5个任务，此时应该有 5 个 worker 线程
        for (int i = 0; i < 5; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getPoolSize());
        Thread.sleep(100);

        // 再提交5个任务，此时应该还是 5 个 worker 线程，因为这些任务先扔进队列中
        for (int i = 0; i < 5; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getPoolSize());
        Thread.sleep(100);

        // 再提交5个任务，此时应该是 10 个 worker 线程
        for (int i = 0; i < 5; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getPoolSize());
        Thread.sleep(100);

        // 再提交5个任务，此时应该还是 10 个 worker 线程，且这五个任务都被丢弃
        for (int i = 0; i < 5; i++) {
            threadPool.execute(r);
        }

        System.out.println(threadPool.getPoolSize());
    }
}


