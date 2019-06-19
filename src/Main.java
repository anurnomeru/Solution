import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * The conservatism in both of these checks may cause unnecessary wake-ups, but only when there are multiple racing acquires/releases, so most need signals now or soon anyway.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock l = new ReentrantReadWriteLock();
        ReadLock readLock = l.readLock();
        WriteLock writeLock = l.writeLock();

        writeLock.lock();
        readLock.lock();
        readLock.lock();
        writeLock.lock();

        readLock.unlock();
        writeLock.unlock();
        readLock.unlock();
        writeLock.unlock();

        System.out.println("111111");



        Thread t = new Thread(() -> { readLock.lock();System.out.println("A"); });
        t.setName("A");
        t.start();
        Thread.sleep(500);

        Thread t1 = new Thread(() -> { readLock.lock();System.out.println("B"); });
        t1.setName("B");
        t1.start();
        Thread.sleep(500);

        Thread t2 = new Thread(() -> { readLock.lock();System.out.println("C"); });
        t2.setName("C");
        t2.start();
        Thread.sleep(500);

        Thread tx = new Thread(() -> { writeLock.lock();System.out.println("另一个写锁线程"); });
        tx.setName("另一个写锁线程");
        tx.start();
        Thread.sleep(500);

        Thread t3 = new Thread(() -> { readLock.lock();System.out.println("D"); });
        t3.setName("D");
        t3.start();
        Thread.sleep(500);

        readLock.unlock();
        writeLock.unlock();
    }
}
