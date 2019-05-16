package aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Anur IjuoKaruKas on 2019/5/7
 */
public class MyAQS extends AbstractQueuedSynchronizer {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.readLock().lock();
        reentrantReadWriteLock.writeLock().lock();
    }
}
