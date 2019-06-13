package wrl;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * Created by Anur IjuoKaruKas on 2019/6/12
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock l = new ReentrantReadWriteLock();
        ReadLock readLock = l.readLock();
        WriteLock writeLock = l.writeLock();

        readLock.lock();
        writeLock.lock();

        new ReentrantLock().lock();
    }
}
