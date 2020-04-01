package cz.jalasoft.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class Server {

	public static void main(String[] args) throws InterruptedException {

		ServerBootstrap bootstrap = new ServerBootstrap();

		EventLoopGroup master =  new NioEventLoopGroup();
		EventLoopGroup workers = new NioEventLoopGroup();

		try {
			bootstrap.group(master, workers);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {

					ServerSocketChannel parent = channel.parent();


					channel.pipeline().addLast(new HttpRequestDecoder(), new HttpResponseEncoder(), new EchoHandler());
				}
			});

			Channel ch = bootstrap.bind(9999).sync().channel();
			ch.closeFuture().sync();

			System.out.println("Nastartovano");

		} finally {
			master.shutdownGracefully();
			workers.shutdownGracefully();
		}
	}
}
