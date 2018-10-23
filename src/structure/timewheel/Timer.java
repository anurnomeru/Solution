package structure.timewheel;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Anur IjuoKaruKas on 2018/10/17
 */
public class Timer {

    public static Random random = new Random();

    public static void main(String[] args) {
        java.util.Timer timer = new java.util.Timer();

        AtomicLong taskCounter = new AtomicLong(0);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 6000000; i++) {
            long ms = random.nextInt(60000);
            taskCounter.incrementAndGet();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    System.out.println("延迟任务消费：" + ms);
                    taskCounter.decrementAndGet();
                }
            }, i);
        }

        while (taskCounter.get() == 0) {
            System.out.println("耗时" + (System.currentTimeMillis() - start));
            break;
        }

        // Timer timer = new Timer();
        //
        // for (int i = 0; i < 6000000; i++) {
        //     timer.taskCounter.incrementAndGet();
        //     long ms = random.nextInt(60000);
        //     timer.addTask(new TimedTask(
        //         ms
        //         ,
        //         () -> {
        //             System.out.println("延迟任务消费：" + ms);
        //             timer.taskCounter.decrementAndGet();
        //         }));
        // }
        //
        // long start = System.currentTimeMillis();
        // while (true) {
        //     timer.advanceClock(20);
        //     if (timer.taskCounter.get() == 0L) {
        //         System.out.println("耗时" + (System.currentTimeMillis() - start));
        //         break;
        //     }
        // }
    }

    /** 计数 */
    public volatile AtomicLong taskCounter = new AtomicLong(0);

    /** 最底层的那个时间轮 */
    private TimeWheel timeWheel;

    /** 对于一个Timer以及附属的时间轮，都只有一个delayQueue */
    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

    private ExecutorService taskExecutor = Executors.newFixedThreadPool(1); // 只有一个线程的无限阻塞队列线程池

    /**
     * 新建一个Timer，同时新建一个时间轮
     */
    public Timer() {
        timeWheel = new TimeWheel(20, 10, System.currentTimeMillis(), delayQueue);
    }

    /**
     * 将任务添加到时间轮
     */
    public void addTask(TimedTask timedTask) {
        if (!timeWheel.addTask(timedTask)) {
            taskExecutor.submit(timedTask.getTask());
        }
    }

    /**
     * 推进一下时间轮的指针，并且将delayQueue中的任务取出来再重新扔进去
     */
    public void advanceClock(long timeout) {
        try {
            // System.out.println(this.getClass() + "：进行 poll 操作");
            Bucket bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            // System.out.println(this.getClass() + "：poll 成功，获取到：" + (bucket == null ? "null" : bucket.toString()));
            if (bucket != null) {
                // System.out.println(this.getClass() + "：推动时间轮前进！");
                timeWheel.advanceClock(bucket.getExpire());
                // System.out.println(this.getClass() + "：重新将bucket内容分配到新的时间轮，并且执行已经过期的任务！");
                bucket.flush(this::addTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
