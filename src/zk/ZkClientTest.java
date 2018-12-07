package zk;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by Anur IjuoKaruKas on 2018/12/6
 */
public class ZkClientTest {

    private static String CONNECT_STR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static class CustomWatcher implements Watcher {

        String sign;

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

    public static void main(String[] args) throws IOException, InterruptedException {

        // 正常连接
        CustomWatcher watcher = new CustomWatcher("non pas");
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STR,
            5 * 1000, watcher);
        System.out.println(zooKeeper.getState());
        watcher.await();
        System.out.println(zooKeeper.getState());
        System.out.println("======================");

        long sessionId = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();

        // 错误连接
        watcher = new CustomWatcher("err pas");
        zooKeeper = new ZooKeeper(CONNECT_STR, 5 * 1000, watcher, 11, new byte[] {1});
        System.out.println(zooKeeper.getState());
        watcher.await();
        System.out.println(zooKeeper.getState());
        System.out.println("======================");

        // 正确连接
        watcher = new CustomWatcher("hav pas");
        zooKeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5 * 1000, watcher, sessionId, password);
        System.out.println(zooKeeper.getState());
        watcher.await();
        System.out.println(zooKeeper.getState());
        System.out.println("======================");

        Thread.sleep(100000000);
    }
}
