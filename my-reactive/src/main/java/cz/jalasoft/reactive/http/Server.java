package cz.jalasoft.reactive.http;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyOutbound;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class Server {

	public static void main(String[] args) {

		DisposableServer server = HttpServer.create()
				.port(8585)
				.route(routes -> routes.get("/info/{data}", Server::handleInfo))
				.bindNow();

		server.onDispose().block();
	}

	private static Publisher<Void> handleInfo(HttpServerRequest request, HttpServerResponse response) {

		String data = request.param("data");

		NettyOutbound out = response.sendString(Mono.just("Zdravim....a diky za " + data));


		return out;
	}
}
