package netty.inandoutboundhanderand;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) {
        new MyClient().run();
    }

    /**
     * 客服端主函数
     */
    private void run() {
       EventLoopGroup group = new NioEventLoopGroup();
       try {
           Bootstrap bootstrap = new Bootstrap();
           bootstrap.group(group)
                   .channel(NioSocketChannel.class)
                   .handler(new MyClientInitializer());
           ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();
           sync.channel().closeFuture().sync();
       }catch (Exception e){
           e.printStackTrace();
       }
       finally {
           group.shutdownGracefully();
       }

    }
}
