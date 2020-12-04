package netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * HttpObject：封装有服务端和客户端相互通讯的数据
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){

            //排除/favicon.ico请求
            HttpRequest request= (HttpRequest) msg;
            URI uri=new URI(request.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("favicon.ico图标资源请求不作处理！");
                return;
            }

            System.out.println("msg类型为：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());



            ByteBuf content = Unpooled.copiedBuffer("我是服务端，你们好！", CharsetUtil.UTF_8);

            //构造一个http的响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
