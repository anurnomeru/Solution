package reactor;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Processor implements Runnable {

    private ConcurrentLinkedQueue<SocketChannel> newConnection;

    private Selector selector;

    public Processor() throws IOException {
        this.newConnection = new ConcurrentLinkedQueue<>();
        this.selector = Selector.open();
    }

    @Override
    public void run() {
        while (true) {

            /*
             * 处理新链接
             */
            while (!newConnection.isEmpty()) {
                SocketChannel socketChannel = newConnection.poll();
                try {
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    selectionKey.attach(socketChannel);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }

            /*
             * 处理新应答
             */
            while (true) {

            }

            // readyKeys 已经准备好了的 SelectionKey 的数量
            int ready = 0;
            try {
                ready = selector.select(500);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ready > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isReadable()) {

                    }
                }
            }

            /*
             * 处理新应答
             */
            while (true) {

            }
        }
    }

    public void accept(SocketChannel socketChannel) {
        newConnection.add(socketChannel);
        // 还需要wakeUp，如果轮询阻塞了，告诉它可以不阻塞了
        selector.wakeup();
    }
}
