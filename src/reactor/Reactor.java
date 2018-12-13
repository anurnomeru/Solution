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

        Processor processor1 = new Processor(requestChannel);
        Processor processor2 = new Processor(requestChannel);
        Acceptor acceptor = new Acceptor(new InetSocketAddress(PORT), new Processor[] {
            processor1,
            processor2
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(acceptor);

        executorService.execute(processor1);
        executorService.execute(processor2);

        Handler handler1 = new Handler(requestChannel);
        Handler handler2 = new Handler(requestChannel);
        executorService.execute(handler1);
        executorService.execute(handler2);
    }
}
