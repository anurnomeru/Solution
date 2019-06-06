package aqs;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anur IjuoKaruKas on 2019/6/4
 */
public class Condition {

    private ReentrantLock reentrantLock = new ReentrantLock();

    private java.util.concurrent.locks.Condition meetWaiter = reentrantLock.newCondition();

    private java.util.concurrent.locks.Condition fruitWaiter = reentrantLock.newCondition();

    private void buyMeet() throws InterruptedException {
        try {
            reentrantLock.lock();
            print("前去买肉发现没货");
            meetWaiter.await();
            print("被通知：肉进货了~");
        } finally {
            reentrantLock.unlock();
        }
    }

    private void buyFruit() throws InterruptedException {
        try {
            reentrantLock.lock();
            print("前去水果发现没货");
            fruitWaiter.await();
            print("被通知：水果进货了~");
        } finally {
            reentrantLock.unlock();
        }
    }

    private void meetIn() {
        try {
            reentrantLock.lock();
            print("通知：肉进货了~");
            meetWaiter.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void fruitIn() {
        try {
            reentrantLock.lock();
            print("通知：水果进货了~");
            fruitWaiter.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Condition condition = new Condition();

        new Thread(() -> {
            try {
                condition.buyFruit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                condition.buyFruit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                condition.buyMeet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        condition.fruitIn();

        Thread.sleep(1000);
        condition.meetIn();
    }

    public static void print(String print) {
        System.out.println(String.format("时间 - %s\t\t%s\t\t%s", new Date(), Thread.currentThread(), print));
    }
}
