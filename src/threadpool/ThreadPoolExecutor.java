package threadpool;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Anur IjuoKaruKas on 2019/7/16
 */
public class ThreadPoolExecutor {

    private final HashSet<Worker> workers = new HashSet<>();

    private volatile ThreadFactory threadFactory;

    private final BlockingQueue<Runnable> workQueue;

    public ThreadPoolExecutor(BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.workQueue = workQueue;
    }

    public void execute(Runnable command) {
        workQueue.offer(command);
    }

    /**
     * 新建一个 worker 线程、启动并纳入 workers
     */
    private boolean addWorker(Runnable firstTask) {
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

    private Runnable getTask() {
        return workQueue.poll();
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
}


