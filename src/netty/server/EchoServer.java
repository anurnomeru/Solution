package netty.server;

import java.net.InetSocketAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * Created by Anur IjuoKaruKas on 2019/1/15
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(9876).start();
    }

    public void start() throws InterruptedException {
        EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                           .channel(NioServerSocketChannel.class)
                           .localAddress(new InetSocketAddress(port))
                           .childHandler(new ChannelInitializer<SocketChannel>() {// 这是关键，当一个新的连接被接受（accept）时，一个新的子channel将会被创建，而ChannelInitializer
                               // 将会把一个你的EchoServerHandler 的实例添加到该Channel的ChannelPipeline中。正如我们之前所解释的，这个ChannelHandler将会受到有关入栈消息的通知

                               @Override
                               protected void initChannel(SocketChannel socketChannel) {
                                   socketChannel.pipeline()
                                                .addLast(serverHandler);
                               }
                           });

            ChannelFuture f = serverBootstrap.bind()
                                             .sync();
            f.channel()
             .closeFuture()
             .sync();
        } finally {
            group.shutdownGracefully()
                 .sync();
        }
    }
}
