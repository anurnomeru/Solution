package zk.common;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
/**
 * Created by Anur IjuoKaruKas on 2018/12/7
 */
public class CustomWatcher implements Watcher {

    public String sign;

    private CountDownLatch latch = new CountDownLatch(1);

    public CustomWatcher(String sign) {
        this.sign = sign;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(sign + " - event: " + watchedEvent);

        if (KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println(sign + " - connect success ");
            latch.countDown();
        } else if (KeeperState.Expired == watchedEvent.getState()) {
            System.out.println(sign + " - connect fail ");
            latch.countDown();
        }
    }

    public void await() throws InterruptedException {
        latch.await();
    }
}
