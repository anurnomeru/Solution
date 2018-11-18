package redis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import structure.timewheel.TimedTask;
import structure.timewheel.Timer;

/**
 * Created by Anur IjuoKaruKas on 2018/11/15
 */
public class DistributeLock {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost");
        DistributeLock distributeLock = new DistributeLock("locking", jedis);
        distributeLock.tryLock(10000L,100L);

        Thread.sleep(10000L);
        distributeLock.unLock();
    }

    private Logger log = LogManager.getLogger();

    private static final int TRY_LOCK_INTERVAL = 200;

    private static final int DEFAULT_LEASE = 2000;

    private static Timer TIMER;

    private String key;

    private Jedis jedis;

    private TimedTask renewTask;

    private Lock readLock;

    private WriteLock writeLock;

    public DistributeLock(String key, Jedis jedis) {
        TIMER = Timer.getInstance();
        this.key = key;
        this.jedis = jedis;

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    private boolean tryLock(Long leaseMs, Long waitMs) {
        leaseMs = leaseMs != null ? leaseMs : DEFAULT_LEASE;

        long startMs = System.currentTimeMillis();
        if (this.doLeaseLock(leaseMs)) {
            log.info("lease the lock of key {} success.", key);
            renew(leaseMs);
            return true;
        } else {
            log.info("lease the lock of key {} fail.", key);
            try {
                Thread.sleep(TRY_LOCK_INTERVAL);
            } catch (InterruptedException ignore) {
            }
            return 0 < (waitMs = waitMs - (System.currentTimeMillis() - startMs)) && this.tryLock(leaseMs, waitMs);
        }
    }

    private void unLock() {
        try {
            writeLock.lock();
            renewTask.cancle();
            this.doReleaseLock();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 使用定时任务去不断续租
     */
    private void renew(long leaseMs) {
        try {
            writeLock.lock();
            if (renewTask == null || !renewTask.isCancle()) {
                renewTask = new TimedTask(leaseMs / 2, () -> {
                    try {
                        readLock.lock();
                        this.doRenew(leaseMs);
                    } finally {
                        readLock.unlock();
                    }
                    this.renew(leaseMs);
                });
                TIMER.addTask(renewTask);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 尝试获得一个锁
     */
    private boolean doLeaseLock(long leaseMs) {
        log.info("try to lease the key {} {}ms", key, leaseMs);
        return jedis.set(key, key, "nx", "ex", leaseMs) != null;
    }

    /**
     * 释放一个锁
     */
    private boolean doReleaseLock() {
        log.info("release the key {}", key);
        return jedis.del(key) != 0L;
    }

    /**
     * +1s
     */
    private boolean doRenew(long renewMs) {
        log.info("renew the lease of key {} {}ms", key, renewMs);
        return jedis.set(key, key, "xx", "ex", renewMs) != null;
    }
}
