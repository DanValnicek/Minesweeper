package servercomm;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String in) throws Exception {
		System.out.println("Server: " + in);
		JsonMessageHandler messageHandler = new JsonMessageHandler((JSONObject) new JSONParser().parse(in));
		messageHandler.showNotification();
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
