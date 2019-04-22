package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import netty.server.EchoServerHandler;

/**
 * Created by Anur IjuoKaruKas on 2019/1/15
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 当channelRead0方法完成时，已经有了传入消息，并且已经处理完它了。当方法返回时，SimpleChannelInboundHandler会释放指向保存该消息的ByteBuf的内存引用
     * 与{@link EchoServerHandler}不同，它仍需要传入消息返回给发送者，而 write 操作是异步的直到 channelRead 方法返回后可能仍然没有完成（2-1）。
     * 为此，EchoServerHandler拓展了ChannelInboundHandlerAdapter，其在这个时间点上不会释放消息。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }
}