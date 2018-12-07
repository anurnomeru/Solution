package zk;

import java.io.IOException;
import org.apache.zookeeper.ZooKeeper;
import zk.common.Constant;
import zk.common.CustomWatcher;

/**
 * Created by Anur IjuoKaruKas on 2018/12/6
 */
public class TestZkClientConnect {

    public static void main(String[] args) throws IOException, InterruptedException {

        // 正常连接
        CustomWatcher watcher = new CustomWatcher("non pas");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR,
            5 * 1000, watcher);
        System.out.println(zooKeeper.getState());
        watcher.await();
        System.out.println(zooKeeper.getState());
        System.out.println("======================");

        long sessionId = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();

        // 错误连接
        watcher = new CustomWatcher("err pas");
        zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5 * 1000, watcher, 11, new byte[] {1});
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
