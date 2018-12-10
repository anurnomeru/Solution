package zk;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import zk.common.Constant;
import zk.common.CustomWatcher;
/**
 * Created by Anur IjuoKaruKas on 2018/12/10
 */
public class TestZkAuthClientCreate {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        createSync();
        //        createAsync();
    }

    public static void createSync() throws IOException, InterruptedException, KeeperException {
        CustomWatcher customWatcher = new CustomWatcher("create - test - sync");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customWatcher);
        customWatcher.await();

        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());

        String path1 = zooKeeper.create("/sanguo", "luoguanzhong".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        System.out.println("Create path " + path1);

        String path2 = zooKeeper.create("/sanguo/wu", "luoguanzhong".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        System.out.println("Create path " + path2);

        Thread.sleep(100000);
    }

    public static void createAsync() throws IOException, InterruptedException, KeeperException {
        CustomWatcher customWatcher = new CustomWatcher("create - test - async");

        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customWatcher);
        customWatcher.await();

        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());

        zooKeeper.create("/sanguo", "luoguanzhong".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL,
            (i, s, o, s1) -> System.out.println(String.format("create path async rc = %s, path = %s, ctx = %s, real path = %s", i, s, o, s1)), "This is context");

        Thread.sleep(1000);

        zooKeeper.create("/sanguo/wu", "luoguanzhong".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL,
            (i, s, o, s1) -> System.out.println(String.format("create path async rc = %s, path = %s, ctx = %s, real path = %s", i, s, o, s1)), "This is context");

        Thread.sleep(100000000);
    }
}
