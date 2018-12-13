package reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by Anur IjuoKaruKas on 2018/12/12
 */
public class Handler implements Runnable {

    private RequestChannel requestChannel;

    private String name;

    public Handler(String name, RequestChannel requestChannel) {
        this.name = name;
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = requestChannel.receiveRequest();
            if (request != null) {
                System.out.println("接收的请求将由" + name + "进行处理");
                //                    Thread.sleep(500);        // 模拟业务处理
                ByteBuffer byteBuffer = request.getByteBuffer();
                handler(request.getSelectionKey(), byteBuffer);
            }
        }
    }

    public void handler(SelectionKey selectionKey, ByteBuffer byteBuffer) {
        byte[] bytes = byteBuffer.array();

        String msg = new String(bytes);

        ByteBuffer response;
        if (msg.startsWith("Fetch")) {
            response = ByteBuffer.allocate(2048);
            response.put("Fetch ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
        } else if (msg.startsWith("Hello")) {
            response = ByteBuffer.allocate(2048);
            response.put("Hi ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
        } else {
            response = ByteBuffer.allocate(2048);
            response.put("Woww ~~~~~~~~~~".getBytes());
            response.put(bytes);
            response.flip();
        }
        System.out.println(name + "处理完毕，正将处理结果返回给Processor");
        requestChannel.sendResponse(new Response(selectionKey, response));
    }
}
