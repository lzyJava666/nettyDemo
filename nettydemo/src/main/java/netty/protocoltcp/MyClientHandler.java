package netty.protocoltcp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg;
        byte[] content;
        int length;
        MessageProtocol messageProtocol;
        for (int i = 0; i < 5; i++) {
            msg=("今天天气真好，吃屎 " + i);
            content=msg.getBytes(CharsetUtil.UTF_8);
            length = content.length;
            messageProtocol=new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("服务端发出的消息为："+new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("长度为："+msg.getLen());
        System.out.println("总共数量为："+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
