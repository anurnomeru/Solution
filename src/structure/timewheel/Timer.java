package structure.timewheel;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anur IjuoKaruKas on 2018/10/17
 */
public class Timer {

    public static Random random = new Random();

    public static void main(String[] args) {
        Timer timer = new Timer();

        for (int i = 0; i < 1000; i++) {
            long ms = TimeUnit.SECONDS.toMillis(random.nextInt(60));
            timer.addTask(new TimedTask(
                ms
                , () -> System.out.println("啦啦啦啦啦啦啦:" + ms)));
        }

        while (true) {
            timer.advanceClock(1000);
        }
    }

    /** 最底层的那个时间轮 */
    private TimeWheel timeWheel;

    /** 对于一个Timer以及附属的时间轮，都只有一个delayQueue */
    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

    private ExecutorService taskExecutor = Executors.newFixedThreadPool(1); // 只有一个线程的无限阻塞队列线程池

    /**
     * 将任务添加到时间轮
     */
    public void addTask(TimedTask timedTask) {
        if (!timeWheel.addTask(timedTask)) {
            taskExecutor.submit(timedTask.getTask());
        }
    }

    /**
     * 新建一个Timer，同时新建一个时间轮
     */
    public Timer() {
        timeWheel = new TimeWheel(20, 10, System.currentTimeMillis(), delayQueue);
    }

    /**
     * 推进一下时间轮的指针，并且将delayQueue中的任务取出来再重新扔进去
     */
    public void advanceClock(long timeout) {
        try {
            Bucket bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                timeWheel.advanceClock(bucket.getExpire());
                bucket.flush(this::addTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
