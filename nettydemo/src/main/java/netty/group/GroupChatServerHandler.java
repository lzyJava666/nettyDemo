package netty.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.time.LocalDateTime;

/**
 * 服务端的主处理handler
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //创建一个存放channel的group组
    public static ChannelGroup channelGroup = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);
    ;

    //读取数据，并且转发
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel == ch) {
                // 自己
                ch.writeAndFlush(msg+"  ---"+ LocalDateTime.now());
            }else{
                ch.writeAndFlush("["+channel.remoteAddress()+"]:"+msg+"  ---"+ LocalDateTime.now());
            }
        });
    }

    //发生异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }

    //每当从服务端收到新的客户端连接时
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("欢迎！[新成员]：" + channel.remoteAddress() + " 加入了聊天室！");
        channelGroup.add(channel);
    }

    //表示channel处理活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[成员]：" + ctx.channel().remoteAddress() + " 上线");
    }

    //表示channel处于非活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[成员]：" + ctx.channel().remoteAddress() + " 下线");
    }

    //断开连接,自动从channelGroup中移除
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[群成员：]" + channel.remoteAddress() + " 退出了聊天室");
        System.out.println("当前聊天室成员人数：" + channelGroup.size());
    }
}
