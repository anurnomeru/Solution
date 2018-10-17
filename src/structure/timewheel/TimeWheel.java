package structure.timewheel;

import java.util.concurrent.DelayQueue;
/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 时间轮，可以推进时间和添加任务
 */
public class TimeWheel {

    /** 一个时间槽的时间 */
    private long tickMs;

    /** 时间轮大小 */
    private int wheelSize;

    /** 时间跨度 */
    private long interval;

    /** 槽 */
    private Bucket[] buckets;

    /** 时间轮指针 */
    private long currentTimestamp;

    /** 上层时间轮 */
    private volatile TimeWheel overflowWheel;

    private DelayQueue<Bucket> delayQueue;

    public TimeWheel(long tickMs, int wheelSize, long currentTimestamp, DelayQueue<Bucket> delayQueue) {
        this.currentTimestamp = currentTimestamp;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.interval = tickMs * wheelSize;
        this.buckets = new Bucket[wheelSize];
        this.currentTimestamp = currentTimestamp - (currentTimestamp % tickMs);
        this.delayQueue = delayQueue;

        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new Bucket();
        }
    }

    private TimeWheel getOverflowWheel() {
        if (overflowWheel == null) {
            synchronized (this) {
                if (overflowWheel == null) {
                    overflowWheel = new TimeWheel(interval, wheelSize, currentTimestamp);
                }
            }
        }
        return overflowWheel;
    }

    public boolean addTask(TimedTask timedTask) {
        if (timedTask == null) {
            return false;
        }

        long delayTimestamp = timedTask.getDelayTime(currentTimestamp);
        if (delayTimestamp <= 0) {
            return false;
        }

        int maybeInThisBucket = (int) (delayTimestamp / tickMs);

        if (maybeInThisBucket < wheelSize) {// 扔进当前时间轮的某个槽中，只有时间【大于某个槽】，才会放进去
            Bucket bucket = buckets[maybeInThisBucket];
            bucket.addTask(timedTask);
        } else {
            TimeWheel timeWheel = getOverflowWheel();// 当maybeInThisBucket大于等于wheelSize时，需要将它扔到上一层的时间轮
            timeWheel.addTask(timedTask);
        }
        return true;
    }

    /**
     * 尝试推进一下指针
     */
    public void advanceClock(long timestamp) {
        if (timestamp - tickMs > currentTimestamp) {
            currentTimestamp = timestamp - (timestamp % tickMs);

            if (overflowWheel != null) {
                this.getOverflowWheel()
                    .advanceClock(timestamp);
            }
        }
    }
}
