package threadpool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Anur IjuoKaruKas on 2019/6/19
 */
public class ThreadPool extends AbstractQueuedSynchronizer {

    private final Queue<Runnable> tasks = new ArrayDeque<>();

    private final long destroyAfterDoNothing;

    private final Runnable template;

    private final int maxThread;

    private static final long sleepNano = TimeUnit.MILLISECONDS.toNanos(100);

    public ThreadPool(int maxThread, long duration, TimeUnit timeUnit) {
        this.destroyAfterDoNothing = timeUnit.toMillis(duration);
        this.maxThread = maxThread;

        this.template = () -> {
            long remainMills = this.destroyAfterDoNothing;
            Runnable r;
            for (; ; ) {
                r = tasks.poll();
                if (r == null) {
                    LockSupport.parkNanos(sleepNano);
                    if ((remainMills -= 100) <= 0) {
                        break;
                    }
                } else {
                    remainMills = this.destroyAfterDoNothing;
                    r.run();
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
        ThreadPool threadPool = new ThreadPool(10, 10, TimeUnit.SECONDS);

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
