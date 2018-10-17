package structure.timewheel;

import java.util.concurrent.Callable;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 需要延迟执行的任务，放在槽 {@link Bucket} 里面
 */
public class TimedTask {

    /** 执行时间 */
    private long executeTimestamp;

    /** 任务 */
    private Callable<Void> task;

    public TimedTask(long executeTimestamp, Callable<Void> task) {
        this.executeTimestamp = executeTimestamp;
        this.task = task;
    }

    /**
     * 0 代表已经到了执行时间
     */
    public long getDelayTime(long currentTimestamp) {
        return Math.min(currentTimestamp - executeTimestamp, 0L);
    }

    public void execute() throws Exception {
        if (task != null) {
            task.call();
        }
    }
}
