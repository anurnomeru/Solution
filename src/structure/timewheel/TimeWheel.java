package structure.timewheel;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 */
public class TimeWheel {

    private long tickMs;// 一个时间槽的时间

    private int wheelSize;// 时间轮大小

    private long interval;// 时间跨度

    private Bucket[] buckets;// 槽

    private long currentTimestamp;

    private volatile TimeWheel overflowWheel;

    public TimeWheel(long tickMs, int wheelSize, long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.interval = tickMs * wheelSize;
        this.buckets = new Bucket[wheelSize];

        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new Bucket();
        }

        this.currentTimestamp = currentTimestamp - (currentTimestamp % tickMs);
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

        if (maybeInThisBucket < wheelSize) {// 扔进当前时间轮的某个槽中
            Bucket bucket = buckets[maybeInThisBucket];
            bucket.addTask(timedTask);
        } else {
            TimeWheel timeWheel = getOverflowWheel();// 当maybeInThisBucket大于等于wheelSize时，需要将它扔到上一层的时间轮
            timeWheel.addTask(timedTask);
        }
        return true;
    }
}
