package netty.inandoutboundhanderand;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import netty.protocoltcp.MessageProtocol;

/**
 * 由于前一个处理完，数据一定为long
 */
public class MyServerHander extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println(" MyServerHander ");
        System.out.println("从客户端"+ctx.channel().remoteAddress()+" :"+new String(msg.getContent(), CharsetUtil.UTF_8));
        ctx.writeAndFlush(12512L);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.channel().closeFuture();
    }
}
