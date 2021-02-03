package netty.inandoutboundhanderand;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入解码器
        pipeline.addLast(new MyServerByteToLongDecoder());
        //加入编码器
        pipeline.addLast(new MyServerLongToByteEncoder());
        pipeline.addLast(new MyServerHander());
    }
}
