package redis;

import java.util.Random;
import redis.clients.jedis.Jedis;
import structure.timewheel.Timer;

/**
 * Created by Anur IjuoKaruKas on 2018/11/15
 */
public class DistributeLock {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        DistributeLock distributeLock = new DistributeLock(jedis);

        Random random = new Random();
        Runnable runnable = () -> System.out.println(distributeLock.tryLock("1", 1000L, 1000L));
        // System.out.println(distributeLock.tryLock("1", 1000L, 1000L));
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        thread.start();
        thread1.start();

        // distributeLock.tryLock("this", 1000);
    }

    private static final int TRY_LOCK_INTERVAL = 200;

    private static final int DEFAULT_LEASE = 2000;

    private static Timer TIMER = Timer.getInstance();

    private Jedis jedis;

    public DistributeLock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean tryLock(String key, Long leaseMs, Long waitMs) {
        long startMs = System.currentTimeMillis();

        if (this.doTryLock(key, leaseMs)) {
            return true;
        } else {
            try {
                Thread.sleep(TRY_LOCK_INTERVAL);
            } catch (InterruptedException ignore) {
            }
            return 0 < (waitMs = waitMs - (System.currentTimeMillis() - startMs)) && this.tryLock(key, leaseMs, waitMs);
        }
    }

    /**
     * 尝试获得一个锁
     */
    private boolean doTryLock(String key, Long leaseMs) {
        return jedis.set(key, key, "nx", "ex", leaseMs != null ? leaseMs : DEFAULT_LEASE) != null;
    }

    /**
     * +1s
     */
    private boolean renew(String key, long renewMs) {
        return jedis.set(key, key, "xx", "ex", renewMs) != null;
    }
}
