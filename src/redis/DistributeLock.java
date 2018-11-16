package redis;

import redis.clients.jedis.Jedis;
import structure.timewheel.TimedTask;
import structure.timewheel.Timer;

/**
 * Created by Anur IjuoKaruKas on 2018/11/15
 */
public class DistributeLock {

    private static final int LOCK_INTERVAL = 200;

    private static Timer TIMER = Timer.getInstance();

    public Jedis jedis;

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        DistributeLock distributeLock = new DistributeLock(jedis);

        TIMER.addTask(new TimedTask(1000, () -> System.out.println("lalala"), DistributeLock.class));

        // distributeLock.tryLock("this", 1000);
    }

    public DistributeLock(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean doTryLock(String key) {
        return jedis.set(key, key, "nx", "ex", LOCK_INTERVAL) != null;
    }
}
