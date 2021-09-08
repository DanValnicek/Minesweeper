package servercomm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String in) throws Exception {
//        System.out.println("idk channelRead0");

        System.out.println(in);
        ReferenceCountUtil.release(in);
    }

    public void exeptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
//        System.out.println("idk exeptionCaught");
        cause.printStackTrace();
        channelHandlerContext.close();
    }

//    @Override
//    public void channelActive(ChannelHandlerContext channelHandlerContext){
//        System.out.println("idk channelActive");
//        Channel out = channelHandlerContext.channel();
//        System.out.println(out);
//    }

}
