package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Reactor {

    public static void main(String[] args) throws IOException {
        ArrayBlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue<Response> responseQueue = new ArrayBlockingQueue<>(100);
        Processor processor = new Processor(requestQueue, responseQueue);

        Acceptor acceptor = new Acceptor(new InetSocketAddress(9999), new Processor[] {processor});

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(acceptor);
    }
}
