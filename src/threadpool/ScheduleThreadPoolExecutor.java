package threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created by Anur IjuoKaruKas on 2019/7/16
 */
public class ScheduleThreadPoolExecutor extends ThreadPoolExecutor {

    public ScheduleThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
        long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
        BiConsumer<Runnable, ThreadPoolExecutor> abortPolicy) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, abortPolicy);
    }
}


