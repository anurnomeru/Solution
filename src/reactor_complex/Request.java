package reactor_complex;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Request {

    private SelectionKey selectionKey;

    private ByteBuffer byteBuffer;

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public Request(SelectionKey selectionKey, ByteBuffer byteBuffer) {
        this.selectionKey = selectionKey;
        this.byteBuffer = byteBuffer;
    }
}
