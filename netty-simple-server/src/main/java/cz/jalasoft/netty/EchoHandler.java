package cz.jalasoft.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;

public final class EchoHandler extends ChannelInboundHandlerAdapter {

	private HttpRequest request;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Spojeni navazano.");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Spojeni ukonceno.");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			this.request = (HttpRequest) msg;
			handleRequest((HttpRequest) msg);
		}

		if (msg instanceof LastHttpContent) {
			handleContent((LastHttpContent) msg);

			ByteBuf buf = ctx.alloc().buffer();
			buf.writeBytes("Ahoj...\n".getBytes());
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

			ctx.writeAndFlush(response);

			if (!HttpUtil.isKeepAlive(request)) {
				ctx.close().addListener(c -> System.out.println("Spojeni uzavrano"));
			}
		}
	}

	private void handleRequest(HttpRequest request) {
		HttpMethod method = request.method();
		String uri = request.uri();

		System.out.println("Prislo " + method + " " + uri);
	}

	private void handleContent(LastHttpContent content) {
		ByteBuf buffer = content.content();
		String string = new String(buffer.array());

		System.out.println("Obsah: " + string);
	}
}
