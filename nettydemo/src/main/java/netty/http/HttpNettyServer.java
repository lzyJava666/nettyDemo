package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpNettyServer {

    public static void main(String[] args) {
        //创建线程组bossGroup,用来处理连接请求
        EventLoopGroup bossGroup= new NioEventLoopGroup();
        //创建线程组workerGroup来处理和客户端交互的事件
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try {
            //创建服务器的启动对象，用于配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(6989).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
