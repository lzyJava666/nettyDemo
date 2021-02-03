package netty.inandoutboundhanderand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyClientLongToByteEncoder extends MessageToByteEncoder<Long> {



    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyClientLongToByteEncoder 被调用了");
        System.out.println("msg:"+msg);
        out.writeLong(msg);
    }
}
