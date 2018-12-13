package reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Handler implements Runnable {

    private RequestChannel requestChannel;

    public Handler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = requestChannel.receiveRequest();
                if (request != null) {
                    Thread.sleep(500);        // 模拟业务处理
                    ByteBuffer byteBuffer = request.getByteBuffer();
                    handler(request.getSelectionKey(), byteBuffer);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handler(SelectionKey selectionKey, ByteBuffer byteBuffer) {
        byte[] bytes = byteBuffer.array();

        String msg = new String(bytes);
        System.out.println("收到了请求：" + msg);

        if (msg.startsWith("Fetch")) {
            ByteBuffer response = ByteBuffer.allocate(2048);
            response.put("Fetch ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
            requestChannel.sendResponse(new Response(selectionKey, response));
        } else if (msg.startsWith("Hello")) {
            ByteBuffer response = ByteBuffer.allocate(2048);
            response.put("Hi ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
            requestChannel.sendResponse(new Response(selectionKey, response));
        } else {
            ByteBuffer response = ByteBuffer.allocate(2048);
            response.put("Woww ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
            requestChannel.sendResponse(new Response(selectionKey, response));
        }
    }
}
