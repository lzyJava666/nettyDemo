package netty.inandoutboundhanderand;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出站的编码器
        pipeline.addLast(new MyClientLongToByteEncoder());
        //入站的解码器
        pipeline.addLast(new MyClientByteToLongDecoder());
        pipeline.addLast(new MyClientHander());
    }
}
