package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();
        byte[] context=new byte[length];
        //读取byte
        in.readBytes(context);
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setContent(context);
        messageProtocol.setLen(length);
        System.out.println(messageProtocol);
        out.add(messageProtocol);
    }
}
