package servercomm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class Client {

	private final String host;
	private final int port;
	@Getter @Setter public boolean loggedIn = false;
	@Getter ChannelFuture channel;
	ChannelFuture lastWriteFuture;
	EventLoopGroup group;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
//        new Client("127.0.0.1", 56850).run();
		new Client("165.22.76.230", 56850).connect();
		//server 165.22.76.230
	}

	public void connect() throws IOException {
		group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
					.channel(NioSocketChannel.class)
					.handler(new ChatClientInitializer());
			channel = bootstrap.connect(host, port).sync();
			channel.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public void disconnect() {
		System.out.println("disconnecting");
		if (channel.channel() == null) return;
		channel.channel().writeAndFlush("disconnect");
		channel.channel().disconnect();

		group.shutdownGracefully();

	}

	public void sendMessage(String message) throws InterruptedException {
		lastWriteFuture = null;
		System.out.println("message sent: " + message);
		lastWriteFuture = channel.channel().writeAndFlush(message + "\r\n");
		if (lastWriteFuture != null) {
			lastWriteFuture.sync();
		}
	}
}