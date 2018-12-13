package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Reactor {

    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        RequestChannel requestChannel = new RequestChannel();

        Handler handler = new Handler(requestChannel);
        Processor processor = new Processor(requestChannel);
        Acceptor acceptor = new Acceptor(new InetSocketAddress(PORT), new Processor[] {processor});

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(handler);
        executorService.execute(processor);
        executorService.execute(acceptor);
    }
}
