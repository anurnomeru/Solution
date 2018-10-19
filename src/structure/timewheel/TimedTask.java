package structure.timewheel;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 需要延迟执行的任务，放在槽 {@link Bucket} 里面
 */
public class TimedTask {

    /** 延迟多久执行时间 */
    private long delayMs;

    /** 任务 */
    private Runnable task;

    public Bucket bucket;

    public TimedTask next;

    public TimedTask pre;

    public TimedTask(long delayMs, Runnable task) {
        this.delayMs = delayMs;
        this.task = task;
        this.bucket = null;
        this.next = null;
        this.pre = null;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delayMs;
    }
}
