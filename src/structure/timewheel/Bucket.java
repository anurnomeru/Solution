package structure.timewheel;

import java.util.Iterator;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 *
 * 槽、或者说桶
 */
public class Bucket implements Delayed {

    /** 当前槽的过期时间 */
    private AtomicLong expiration = new AtomicLong(-1L);

    /** 根节点 */
    private TimedTask root = new TimedTask(-1L, null);

    {
        root.pre = root;
        root.next = root;
    }

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

        if (timedTask.bucket == null) {
            TimedTask tail = root.pre;

            timedTask.bucket = this;


            timedTask.pre = root;
        }

        return timedTaskList.add(timedTask);
    }

    /**
     * 重新分配槽
     */
    public synchronized void flush(Consumer<TimedTask> flush) {
        Iterator<TimedTask> timedTaskIterator = timedTaskList.iterator();
        TimedTask timedTask;
        while (timedTaskIterator.hasNext()) {
            timedTask = timedTaskIterator.next();
            timedTaskIterator.remove();
            flush.accept(timedTask);
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS))
            ;
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof Bucket) {
            return Long.compare(expiration.get(), ((Bucket) o).expiration.get());
        }
        return 0;
    }
}
