package reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Processor implements Runnable {

    private ConcurrentLinkedQueue<SocketChannel> newConnection;

    private Selector selector;

    private ArrayBlockingQueue<Request> requestQueue;

    private ArrayBlockingQueue<Response> responseQueue;

    public Processor(ArrayBlockingQueue<Request> requestQueue, ArrayBlockingQueue<Response> responseQueue) throws IOException {
        this.newConnection = new ConcurrentLinkedQueue<>();
        this.selector = Selector.open();
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
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
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }

            /*
             * 处理新应答
             */
            Response response = responseQueue.poll();
            while (response != null) {
                SelectionKey key = response.getSelectionKey();
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                key.attach(response.getByteBuffer());
                response = responseQueue.poll();
            }

            /*
             * 处理新请求 && 新应答
             */
            int ready = 0; // 半秒轮询一次
            try {
                ready = selector.select(500);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ready > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = ((SocketChannel) selectionKey.attachment());
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);// 懒得定协议，就默认取这么多吧 = =
                        try {
                            socketChannel.read(byteBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        requestQueue.add(new Request(socketChannel, byteBuffer));// 接受完数据后，把数据丢进队列
                    }
                }
            }
        }
    }

    public void accept(SocketChannel socketChannel) {
        newConnection.add(socketChannel);
        // 还需要wakeUp，如果轮询阻塞了，告诉它可以不阻塞了
        selector.wakeup();
    }
}
