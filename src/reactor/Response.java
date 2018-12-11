package reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
/**
 * Created by Anur IjuoKaruKas on 2018/12/11
 */
public class Response {

    private SelectionKey selectionKey;

    private ByteBuffer byteBuffer;

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public Response(SelectionKey selectionKey, ByteBuffer byteBuffer) {
        this.selectionKey = selectionKey;
        this.byteBuffer = byteBuffer;
    }
}
