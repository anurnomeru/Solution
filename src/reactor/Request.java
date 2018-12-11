package reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Request {

    private SocketChannel socketChannel;

    private ByteBuffer byteBuffer;

    public Request(SocketChannel socketChannel, ByteBuffer byteBuffer) {
        this.socketChannel = socketChannel;
        this.byteBuffer = byteBuffer;
    }
}
