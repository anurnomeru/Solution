package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Reactor {

    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        ArrayBlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue<Response> responseQueue = new ArrayBlockingQueue<>(100);
        Processor processor = new Processor(requestQueue, responseQueue);
        Acceptor acceptor = new Acceptor(new InetSocketAddress(PORT), new Processor[] {processor});
        Handler handler = new Handler(requestQueue, responseQueue);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(acceptor);
        executorService.execute(processor);
        executorService.execute(handler);
    }
}
