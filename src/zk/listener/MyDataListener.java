package zk.listener;

import org.I0Itec.zkclient.IZkDataListener;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by Anur IjuoKaruKas on 2018/12/27
 */
public class MyDataListener implements IZkDataListener {

    @Override
    public void handleDataChange(java.lang.String s, Object o) throws Exception {
        System.out.println(s + o.toString());
    }

    @Override
    public void handleDataDeleted(java.lang.String s) throws Exception {
        System.out.println(s);
    }
}
