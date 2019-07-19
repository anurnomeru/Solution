package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by Anur IjuoKaruKas on 2019/6/19
 */
public class ThreadPool extends AbstractQueuedSynchronizer {

    private final ArrayBlockingQueue<Runnable> tasks;

    private final long destroyAfterDoNothing;

    private final Runnable template;

    private final int maxThread;

    public ThreadPool(int maxThread, long duration, TimeUnit timeUnit, int queueSize) {
        this.tasks = new ArrayBlockingQueue<>(queueSize);
        this.destroyAfterDoNothing = timeUnit.toMillis(duration);
        this.maxThread = maxThread;

        this.template = () -> {
            long remainMills = this.destroyAfterDoNothing;
            Runnable r;
            for (; ; ) {
                try {
                    r = tasks.poll(100, TimeUnit.MILLISECONDS);
                    if (r == null) {
                        if ((remainMills -= 100) <= 0) {
                            break;
                        }
                    } else {
                        remainMills = this.destroyAfterDoNothing;
                        r.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int state;
            while (!compareAndSetState(state = getState(), state - 1)) {
            }
        };
    }

    public void execute(Runnable runnable) {
        int state = getState();
        tasks.add(runnable);
        if (state != maxThread && tasks.size() > 0) {
            if (compareAndSetState(state, state + 1)) {
                new Thread(template).start();
            }
        }
    }

    public int getRunningThreadSize() {
        return getState();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10, 10, TimeUnit.SECONDS, 100);

        Runnable r = () -> {
        };
        for (int i = 0; i < 5; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getRunningThreadSize());

        Thread.sleep(100);

        for (int i = 0; i < 4; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getRunningThreadSize());

        for (int i = 0; i < 100; i++) {
            threadPool.execute(r);
        }
        System.out.println(threadPool.getRunningThreadSize());

        Thread.sleep(15000);
        System.out.println(threadPool.getRunningThreadSize());
    }
}
