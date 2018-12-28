package reactor_complex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Acceptor implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Acceptor.class.getSimpleName());

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private Processor[] processors;

    private ExecutorService processorsPool;

    public Acceptor(InetSocketAddress inetSocketAddress, Processor[] processors) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket()
                           .bind(inetSocketAddress);
        this.serverSocketChannel = serverSocketChannel;
        this.selector = Selector.open();
        this.processors = processors;

        processorsPool = Executors.newFixedThreadPool(processors.length, new ThreadFactoryBuilder().setNameFormat("Processors - pool ")
                                                                                                   .build());

        for (Processor processor : processors) {
            processorsPool.execute(processor);
        }
    }

    @Override
    public void run() {
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }

        logger.info("已可以开始建立连接");
        int currentProcessors = 0;
        while (true) {
            try {
                int ready = selector.select(500); // 半秒轮询一次
                if (ready > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys()
                                                                   .iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey selectionKey = selectionKeys.next();
                        selectionKeys.remove();
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
    }

    // 建立连接，并且使用RoundRobin分配给一个Processor，也就是负责IO的角色
    public void accept(SelectionKey selectionKey, Processor processor) throws IOException {
        SelectableChannel channel = selectionKey.channel();
        SocketChannel socketChannel = ((ServerSocketChannel) channel).accept();
        socketChannel.configureBlocking(false);
        socketChannel.socket()
                     .setTcpNoDelay(true);
        socketChannel.socket()
                     .setKeepAlive(true);

        // 将需要连接的socketChannel转交给processor去处理
        processor.accept(socketChannel);
    }
}
