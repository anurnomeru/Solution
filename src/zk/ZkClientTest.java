package zk;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
/**
 * Created by Anur IjuoKaruKas on 2018/12/6
 */
public class ZkClientTest {

    private static CountDownLatch latch = new CountDownLatch(1);

    private static String CONNECT_STR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static class CustomWatcher implements Watcher {

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("type: " + watchedEvent.getType());
            System.out.println("path: " + watchedEvent.getPath());
            System.out.println("state: " + watchedEvent.getState());
            System.out.println("wrapper: " + watchedEvent.getWrapper()
                                                         .toString());
            System.out.println("event: " + watchedEvent);

            if (KeeperState.SyncConnected == watchedEvent.getState()) {
                latch.countDown();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STR,
            5 * 1000, watchedEvent -> {

        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("Zk session established.");
        }

        long sessionId = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();
        System.out.println("sessionId :" + sessionId);
        System.out.println("password :" + Arrays.toString(password));

        zooKeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5 * 1000, watchedEvent -> {

        }, 11, new byte[] {1});

        zooKeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5 * 1000, watchedEvent -> {
            System.out.println("type: " + watchedEvent.getType());
            System.out.println("path: " + watchedEvent.getPath());
            System.out.println("state: " + watchedEvent.getState());
            System.out.println("wrapper: " + watchedEvent.getWrapper()
                                                         .toString());

            process(watchedEvent);
        }, sessionId, password);

        System.out.println(zooKeeper.getState());
        Thread.sleep(1000);
    }
}
