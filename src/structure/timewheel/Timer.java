package structure.timewheel;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anur IjuoKaruKas on 2018/10/17
 */
public class Timer {

    /** 最底层的那个时间轮 */
    private TimeWheel timeWheel;

    /** 对于一个Timer以及附属的时间轮，都只有一个delayQueue */
    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

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
        timeWheel.addTask(timedTask);
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
