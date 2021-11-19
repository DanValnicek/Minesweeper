package servercomm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

	private final String host;
	private final int port;
	ChannelFuture channel;
	ChannelFuture lastWriteFuture;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
//        new Client("127.0.0.1", 56850).run();
		new Client("165.22.76.230", 56850).run();
		//server 165.22.76.230
	}

	public void run() throws IOException {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.channel(NioSocketChannel.class)
					.handler(new ChatClientInitializer());
			channel = bootstrap.connect(host, port).sync();

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			lastWriteFuture = null;
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				lastWriteFuture = channel.channel().writeAndFlush(line + "\r\n");
				if ("bye".equalsIgnoreCase(line)) {
					channel.channel().closeFuture().sync();
					break;
				}
			}
			if (lastWriteFuture != null) {
				lastWriteFuture.sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}

	public void sendMessage(String message) throws InterruptedException {
		lastWriteFuture = null;
		lastWriteFuture = channel.channel().writeAndFlush(message + "\r\n");
		if (lastWriteFuture != null) {
			lastWriteFuture.sync();
		}
	}

//    public Client(String host, int port, final String message, final AtomicInteger messageWritten, final AtomicInteger messageRead) throws IOException {
//        //create a socket channel
//        AsynchronousSocketChannel sockChannel = AsynchronousSocketChannel.open();
//
//        //try to connect to the server side
//        sockChannel.connect(new InetSocketAddress(host, port), sockChannel, new CompletionHandler<Void, AsynchronousSocketChannel>() {
//            @Override
//            public void completed(Void result, AsynchronousSocketChannel channel) {
//                //start to read message
//                startRead(channel, messageRead);
//
//                //write an message to server side
//                startWrite(channel, message, messageWritten);
//            }
//
//            @Override
//            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
//                System.out.println("fail to connect to server");
//            }
//
//        });
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        try {
//            AtomicInteger messageWritten = new AtomicInteger(0);
//            AtomicInteger messageRead = new AtomicInteger(0);
//
//            for (int i = 0; i < 1000; i++) {
//                new Client("165.22.76.230", 56850, "echo test", messageWritten, messageRead);
//            }
//
//            while (messageRead.get() != 1000) {
//                Thread.sleep(1000);
//                System.out.println("message write:" + messageWritten);
//                System.out.println("message read:" + messageRead);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void startRead(final AsynchronousSocketChannel sockChannel, final AtomicInteger messageRead) {
//        final ByteBuffer buf = ByteBuffer.allocate(2048);
//
//        sockChannel.read(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
//
//            @Override
//            public void completed(Integer result, AsynchronousSocketChannel channel) {
//                //message is read from server
//                messageRead.getAndIncrement();
//
//                //print the message
//                System.out.println("Read message:" + new String(buf.array()));
//            }
//
//            @Override
//            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
//                System.out.println("fail to read message from server");
//            }
//
//        });
//
//    }
//
//    private void startWrite(final AsynchronousSocketChannel sockChannel, final String message, final AtomicInteger messageWritten) {
//        ByteBuffer buf = ByteBuffer.allocate(2048);
//        buf.put(message.getBytes());
//        buf.flip();
//        messageWritten.getAndIncrement();
//        sockChannel.write(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
//            @Override
//            public void completed(Integer result, AsynchronousSocketChannel channel) {
//                //after message written
//                //NOTHING TO DO
//            }
//
//            @Override
//            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
//                System.out.println("Fail to write the message to server");
//            }
//        });
//    }

}
