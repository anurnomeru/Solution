package structure.timewheel;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anur IjuoKaruKas on 2018/10/17
 */
public class Timer {

    private TimeWheel timeWheel;

    /**
     * 对于一个Timer以及附属的时间轮
     */
    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

    public Timer() {
        timeWheel = new TimeWheel(20, 10, System.currentTimeMillis(), delayQueue);
    }

    public void add(TimedTask timedTask) {
        timeWheel.addTask(timedTask);
    }

    /**
     * 推进一下时间轮的指针，并且将delayQueue中的任务取出来执行一下
     */
    public void advanceClock(long timeout) {
        try {
            Bucket bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                timeWheel.advanceClock(bucket.getExpire());
                bucket.exec();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
