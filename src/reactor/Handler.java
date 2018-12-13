package reactor;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Handler implements Runnable {

    private ArrayBlockingQueue<Request> requestQueue;

    private ArrayBlockingQueue<Response> responseQueue;

    public Handler(ArrayBlockingQueue<Request> requestQueue, ArrayBlockingQueue<Response> responseQueue) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = requestQueue.poll();
                if (request != null) {
                    handler(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handler(Request request) throws InterruptedException {
        Thread.sleep(500);        // 模拟业务处理
        ByteBuffer byteBuffer = request.getByteBuffer();
        byte[] bytes = byteBuffer.array();

        System.out.println("收到了请求：" + new String(bytes));

        ByteBuffer response = ByteBuffer.allocate(2048);
        response.put("This is response".getBytes());
        response.put(bytes);
        response.flip();
        responseQueue.put(new Response(request.getSelectionKey(), response));
    }
}
