package reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Processor implements Runnable {

    private String name;

    private ConcurrentLinkedQueue<SocketChannel> newConnection;

    private Selector selector;

    private ConcurrentHashMap<SelectionKey, ArrayBlockingQueue<ByteBuffer>> inFlightResponse;

    private RequestChannel requestChannel;

    public Processor(String name, RequestChannel requestChannel, ConcurrentHashMap<SelectionKey, ArrayBlockingQueue<ByteBuffer>> inFlightResponse) throws IOException {
        this.name = name;
        this.newConnection = new ConcurrentLinkedQueue<>();
        this.selector = Selector.open();
        this.inFlightResponse = inFlightResponse;
        this.requestChannel = requestChannel;
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
            Response response = requestChannel.receiveResponse();
            while (response != null) {
                SelectionKey key = response.getSelectionKey();
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);

                ArrayBlockingQueue<ByteBuffer> inFlight = inFlightResponse.getOrDefault(response.getSelectionKey(), new ArrayBlockingQueue<>(100));
                inFlightResponse.put(response.getSelectionKey(), inFlight);
                try {
                    inFlight.put(response.getByteBuffer());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = requestChannel.receiveResponse();
            }

            int ready = 0; // 半秒轮询一次
            try {
                ready = selector.select(500);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ready > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {

                    /*
                     * 处理新请求
                     */
                    if (selectionKey.isReadable()) {
                        System.out.println(name + "正在处理新请求");
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);// 懒得定协议，就默认取这么多吧 = =
                        try {
                            socketChannel.read(byteBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byteBuffer.flip();
                        requestChannel.sendRequest(new Request(selectionKey, byteBuffer));// 接受完数据后，把数据丢进队列
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_READ);// 不再关注read
                    }

                    /*
                     * 处理新应答
                     */
                    if (selectionKey.isWritable()) {
                        System.out.println(name + "正在处理新应答");
                        ByteBuffer send = inFlightResponse.get(selectionKey)
                                                          .poll();
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        try {
                            socketChannel.write(send);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_READ);
                    }
                }
            }
        }
    }

    protected void accept(SocketChannel socketChannel) {
        try {
            System.out.println(name + "正在与" + socketChannel.getLocalAddress() + "建立连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
        newConnection.add(socketChannel);
        // 还需要wakeUp，如果轮询阻塞了，告诉它可以不阻塞了
        selector.wakeup();
    }
}
