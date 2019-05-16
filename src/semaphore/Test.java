package semaphore;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Anur IjuoKaruKas on 2019/5/6
 */
public class Test {

    public static void main(String[] args) {

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        reentrantReadWriteLock.readLock()
                              .lock();
        reentrantReadWriteLock.writeLock()
                              .lock();
    }
}
