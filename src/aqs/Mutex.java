package aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by Anur IjuoKaruKas on 2019/5/7
 */
public class Mutex extends AbstractQueuedSynchronizer {

    public static class Sync extends AbstractQueuedSynchronizer {

        public Sync() {
            setState(100); // set the initial state, being unlocked.
        }

        @Override
        protected boolean tryAcquire(int ignore) {
            return compareAndSetState(100, 1);
        }

        @Override
        protected boolean tryRelease(int ignore) {
            setState(100);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(0);
    }

    public void unLock() {
        sync.release(0);
    }

    public static void main(String[] args) throws InterruptedException {
        Mutex mutex = new Mutex();
        mutex.lock();

        Thread thread = new Thread(() -> {
            mutex.lock();
            System.out.println("获取到了锁");
        });

        thread.start();

        System.out.println("SLEEP");
        Thread.sleep(5000);
        mutex.unLock();
    }
}
