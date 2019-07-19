package threadpool;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Created by Anur IjuoKaruKas on 2019/7/16
 */
public class ScheduleThreadPoolExecutor extends ThreadPoolExecutor {

    public ScheduleThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
        long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
        BiConsumer<Runnable, ThreadPoolExecutor> abortPolicy) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, abortPolicy);
    }

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);
        scheduled.schedule();

        //        scheduled.schedule(r, 1, TimeUnit.SECONDS);
        //                scheduled.scheduleAtFixedRate(r, 1, 1, TimeUnit.NANOSECONDS);
        //        scheduled.scheduleWithFixedDelay(r, 1, 1, TimeUnit.NANOSECONDS);

        //        ScheduleThreadPoolExecutor threadPool = new ScheduleThreadPoolExecutor(5, 10,
        //            1, TimeUnit.MINUTES,
        //            new LinkedBlockingQueue<>(5), new ThreadFactoryBuilder().build(),
        //            (runnable, threadPoolExecutor) -> {
        //                // 仿照 DiscardPolicy
        //                // Does nothing, which has the effect of discarding task r.
        //                System.out.println("有任务被丢弃了");
        //            });
        //
        //        // 提交5个任务，此时应该有 5 个 worker 线程
        //        for (int i = 0; i < 5; i++) {
        //            threadPool.execute(r);
        //        }
        //        System.out.println(threadPool.getPoolSize());
        //        Thread.sleep(100);
        //
        //        // 再提交5个任务，此时应该还是 5 个 worker 线程，因为这些任务先扔进队列中
        //        for (int i = 0; i < 5; i++) {
        //            threadPool.execute(r);
        //        }
        //        System.out.println(threadPool.getPoolSize());
        //        Thread.sleep(100);
        //
        //        // 再提交5个任务，此时应该是 10 个 worker 线程
        //        for (int i = 0; i < 5; i++) {
        //            threadPool.execute(r);
        //        }
        //        System.out.println(threadPool.getPoolSize());
        //        Thread.sleep(100);
        //
        //        // 再提交5个任务，此时应该还是 10 个 worker 线程，且这五个任务都被丢弃
        //        for (int i = 0; i < 5; i++) {
        //            threadPool.execute(r);
        //        }
        //
        //        System.out.println(threadPool.getPoolSize());
    }
}


