package servercomm;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String in) throws Exception {
//        System.out.println("idk channelRead0");
	/*	Platform.runLater(() -> {
					Popup popup = new Popup();
					Stage popupStage = new Stage();
//			Main.getFirstStage().getScene().lookup("#errorBox").setAccessibleText(in);
//		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//					errorAlert.setHeaderText("Alert");
//					errorAlert.setContentText(in);
//					errorAlert.showAndWait();
					Label message = new Label(in);
					popupStage.setScene(Main.getFirstStage().getScene());

					message.setTextFill(Color.RED);
					popup.getContent().add(message);
					popup.setAutoFix(true);
					popup.setX(Main.getFirstStage().getWidth());

					popup.show(Main.getFirstStage());
					popupStage.show();
//					Main.getFirstStage().getScene().

				}
		);
				*/
//		if(in.startsWith("error:")){
//			Main.getFirstStage().getScene();
//		}
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
