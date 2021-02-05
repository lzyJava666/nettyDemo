package netty.copypackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes=new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        System.out.println("服务器收到消息："+new String(bytes, CharsetUtil.UTF_8));
        System.out.println("服务器数据量："+(++count));

        //回送消息
        ByteBuf buf = Unpooled.copiedBuffer(UUID.randomUUID() + "", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
