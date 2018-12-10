package zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import zk.common.Constant;

/**
 * Created by Anur IjuoKaruKas on 2018/12/7
 */
public class TestZkClientGetData {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Stat stat = new Stat();
        CustomGetDataWatcher customGetChildrenWatcher = new CustomGetDataWatcher("get - data - test - sync");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customGetChildrenWatcher);
        customGetChildrenWatcher.setZooKeeper(zooKeeper);
        customGetChildrenWatcher.setStat(stat);
        customGetChildrenWatcher.await();

        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.getData("/sanguo111", true, stat);

        Thread.sleep(10000000);
    }

    public static class CustomGetDataWatcher implements Watcher {

        public String sign;

        public ZooKeeper zooKeeper = null;

        public Stat stat = null;

        private CountDownLatch latch = new CountDownLatch(1);

        public CustomGetDataWatcher(String sign) {
            this.sign = sign;
        }

        public void setZooKeeper(ZooKeeper zooKeeper) {
            this.zooKeeper = zooKeeper;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(sign + " - event: " + watchedEvent);

            if (KeeperState.SyncConnected == watchedEvent.getState()) {
                if (EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                    System.out.println(sign + " - connect success ");
                    latch.countDown();
                } else if (EventType.NodeChildrenChanged == watchedEvent.getType()) {
                    try {
                        System.out.println("Reget Child: " + zooKeeper.getChildren(watchedEvent.getPath(), true));// 重新注册
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (EventType.NodeDataChanged == watchedEvent.getType()) {
                    try {
                        System.out.println("Now stat is: " + stat.toString());
                        System.out.println("Node data changed: " + new String(zooKeeper.getData(watchedEvent.getPath(), true, stat)));// 重新注册
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
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
