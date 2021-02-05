package netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MyMessageToByteEncoder());//添加编码器
        ch.pipeline().addLast(new MyByteToMessageDecoder());//添加解码器
        ch.pipeline().addLast(new MyClientHandler());
    }
}
