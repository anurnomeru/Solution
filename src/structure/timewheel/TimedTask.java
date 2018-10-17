package structure.timewheel;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 */
public class TimedTask {

    /** 执行时间 */
    private long executeTimestamp;

    /** 任务 */
    private Runnable task;

    public TimedTask(long executeTimestamp, Runnable task) {
        this.executeTimestamp = executeTimestamp;
        this.task = task;
    }

    /**
     * 0 代表已经到了执行时间
     */
    public long getDelayTime(long currentTimestamp) {
        return Math.min(currentTimestamp - executeTimestamp, 0L);
    }
}
