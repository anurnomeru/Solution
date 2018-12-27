package zk.listener;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * Created by Anur IjuoKaruKas on 2018/12/27
 */
public class test {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("localhost:2181", 3000);
        zkClient.setZkSerializer(new ZkSerializer() {

            @Override
            public byte[] serialize(Object o) throws ZkMarshallingError {
                return o.toString()
                        .getBytes();
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                String s = new String(bytes);
                return s;
            }
        });
        zkClient.subscribeDataChanges("/sanguozhi", new MyDataListener());
        Thread.sleep(100000);
    }
}
