package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("服务器收到消息："+new String(msg.getContent(), CharsetUtil.UTF_8));
        System.out.println("服务器数据量："+(++count));

        //回送消息
        String s = UUID.randomUUID().toString();
        byte[] context=s.getBytes(CharsetUtil.UTF_8);
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setLen(context.length);
        messageProtocol.setContent(context);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
