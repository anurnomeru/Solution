package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Acceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private Processor[] processors;

    public Acceptor(InetSocketAddress inetSocketAddress, Processor[] processors) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket()
                           .bind(inetSocketAddress);
        this.serverSocketChannel = serverSocketChannel;
        this.selector = Selector.open();
        this.processors = processors;
    }

    @Override
    public void run() {
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }

        try {
            int currentProcessors = 0;

            int ready = selector.select(500); // 半秒轮询一次
            if (ready > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isAcceptable()) {
                        this.accept(selectionKey, processors[currentProcessors]);
                        currentProcessors = (currentProcessors + 1) % processors.length;
                    } else {
                        throw new RuntimeException("不应该出现的情况，因为只订阅了OP_ACCEPT");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept(SelectionKey selectionKey, Processor processor) {

    }
}
