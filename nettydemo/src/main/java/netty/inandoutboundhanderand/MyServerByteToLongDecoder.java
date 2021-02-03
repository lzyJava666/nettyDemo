package netty.inandoutboundhanderand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyServerByteToLongDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param ctx 上下文对象
     * @param in 入站的ByteBuf
     * @param out list 集合，将解码后的数据传给下一个hander处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyServerByteToLongDecoder ");
        //判断8个字节以上，便读取一个long
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
