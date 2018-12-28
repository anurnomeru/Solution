package reactor_complex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Reactor {

    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        RequestChannel requestChannel = new RequestChannel();
        ConcurrentHashMap<SelectionKey, ArrayBlockingQueue<ByteBuffer>> inFlightResponse = new ConcurrentHashMap<>();

        Processor processor1 = new Processor("p1", requestChannel, inFlightResponse);
        Processor processor2 = new Processor("p2", requestChannel, inFlightResponse);
        Acceptor acceptor = new Acceptor(new InetSocketAddress(PORT), new Processor[] {
            processor1,
            processor2
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);// 偷懒就用同一个线程池了
        executorService.execute(acceptor);

        Handler handler1 = new Handler("h1", requestChannel);
        Handler handler2 = new Handler("h2", requestChannel);
        executorService.execute(handler1);
        executorService.execute(handler2);
    }
}
