package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("客户端发送的消息为："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址为："+ctx.channel().remoteAddress());
        //通知客户端已接收到消息
        //添加到定时任务中（异步），可加入多个，在同个线程中
        ctx.channel().eventLoop().execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("已读到消息！",CharsetUtil.UTF_8));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //定时任务与普通任务一起执行，在定时任务中的普通任务执行时堵塞
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                ctx.writeAndFlush(Unpooled.copiedBuffer("已读到消息2222！",CharsetUtil.UTF_8));
            }
        },2, TimeUnit.SECONDS);
        //TimeUnit.DAYS          //天
        //TimeUnit.HOURS         //小时
        //TimeUnit.MINUTES       //分钟
        //TimeUnit.SECONDS       //秒
        //TimeUnit.MILLISECONDS  //毫秒
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，哈哈客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
