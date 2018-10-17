package structure.timewheel;

import java.util.concurrent.Callable;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 需要延迟执行的任务，放在槽 {@link Bucket} 里面
 */
public class TimedTask {

    /** 延迟多久执行时间 */
    private long delayMs;

    /** 任务 */
    private Callable<Void> task;

    public TimedTask(long delayMs, Callable<Void> task) {
        this.delayMs = delayMs;
        this.task = task;
    }

    public void execute() throws Exception {
        if (task != null) {
            task.call();
        }
    }

    public long getDelayMs() {
        return delayMs;
    }
}
