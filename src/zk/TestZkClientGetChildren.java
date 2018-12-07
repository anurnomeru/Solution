package zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import zk.common.Constant;

/**
 * Created by Anur IjuoKaruKas on 2018/12/7
 */
public class TestZkClientGetChildren {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        CustomGetChildrenWatcher customGetChildrenWatcher = new CustomGetChildrenWatcher("create - test - sync");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customGetChildrenWatcher);
        customGetChildrenWatcher.setZooKeeper(zooKeeper);
        customGetChildrenWatcher.await();

        System.out.println(zooKeeper.getChildren("/sanguo", true));

        Thread.sleep(5000);
        zooKeeper.create("/sanguo/guo", "guohui".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        zooKeeper.create("/sanguo/guo", "guohui".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        zooKeeper.create("/sanguo/guo", "guohui".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        zooKeeper.create("/sanguo/guo", "guohui".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        zooKeeper.create("/sanguo/guo", "guohui".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        Thread.sleep(50000000);
    }

    public static class CustomGetChildrenWatcher implements Watcher {

        public String sign;

        public ZooKeeper zooKeeper = null;

        private CountDownLatch latch = new CountDownLatch(1);

        public CustomGetChildrenWatcher(String sign) {
            this.sign = sign;
        }

        public void setZooKeeper(ZooKeeper zooKeeper) {
            this.zooKeeper = zooKeeper;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(sign + " - event: " + watchedEvent);

            if (KeeperState.SyncConnected == watchedEvent.getState()) {
                if (EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                    System.out.println(sign + " - connect success ");
                    latch.countDown();
                } else if (EventType.NodeChildrenChanged == watchedEvent.getType()) {
                    //                    try {
                    //                        System.out.println("Reget Child: " + zooKeeper.getChildren(watchedEvent.getPath(), false));// 重新注册
                    //                    } catch (KeeperException | InterruptedException e) {
                    //                        e.printStackTrace();
                    //                    }
                }
            } else if (KeeperState.Expired == watchedEvent.getState()) {
                System.out.println(sign + " - connect fail ");
                latch.countDown();
            }
        }

        public void await() throws InterruptedException {
            latch.await();
        }
    }
}
