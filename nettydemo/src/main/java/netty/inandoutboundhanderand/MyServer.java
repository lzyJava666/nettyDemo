package netty.inandoutboundhanderand;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 入站出站服务端
 */
public class MyServer {
    public static void main(String[] args) {
       new MyServer().run();
    }

    /**
     * 运行主函数
     */
    private void run() {
        EventLoopGroup boosGroup=new NioEventLoopGroup(1);
        EventLoopGroup workGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());
            System.out.println("服务器已就绪");
            ChannelFuture sync = bootstrap.bind(9999).sync();
            sync.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
