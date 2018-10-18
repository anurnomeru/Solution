package structure.timewheel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 槽、或者说桶
 */
public class Bucket implements Delayed {

    /** 当前槽的过期时间 */
    private AtomicLong expiration = new AtomicLong(-1L);

    private List<TimedTask> timedTaskList = new ArrayList<>();

    /**
     * 设置某个槽的过期时间
     */
    public boolean setExpire(long expire) {
        return expiration.getAndSet(expire) != expire;
    }

    /**
     * 获取某个槽的过期时间
     */
    public long getExpire() {
        return expiration.get();
    }

    /**
     * 新增任务到链表
     */
    public synchronized boolean addTask(TimedTask timedTask) {
        return timedTaskList.add(timedTask);
    }

    /**
     * 将某个任务删除
     */
    public synchronized boolean removeTask(TimedTask timedTask) {
        return timedTaskList.remove(timedTask);
    }

    /**
     * 重新分配槽
     */
    public synchronized void flush(Consumer<TimedTask> flush) {
        timedTaskList.iterator()
                     .forEachRemaining(timedTask -> {
                         timedTaskList.remove(timedTask);
                         flush.accept(timedTask);
                     });
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expiration.get(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof Bucket) {

            return Long.compare(expiration.get(), ((Bucket) o).expiration.get());
        }
        return 0;
    }
}
