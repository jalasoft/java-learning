package cz.jalasoft.reactor.netty.learning.countingserver;

import io.netty.buffer.ByteBuf;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

/**
 * @author Jan Lastovicka
 * @since 21/12/2019
 */
public class Main {

    public static void main(String[] args) {

        Mono<? extends DisposableServer> s = TcpServer.create()
                .port(8484)
                /*.doOnConnection(c -> {
                    System.out.println("Spojeni navazano s " + c.address());
                    c.outbound().sendString(Mono.just("Zdravim....")).then();
                })*/
                .handle((in, out) -> {
                    System.out.println("baf");

                    Flux<String> flux = Flux.range(0, 20)
                            .map(i -> 20 - i)
                            .delayElements(Duration.ofMillis(300))
                            .map(i -> "" + i + "\n")
                            .concatWith(Mono.just("BOOOOOOM\n"));

                    return out.sendString(flux).neverComplete();
                })
                .bind();

        System.out.println("Inicializuju server");

        DisposableServer server = s.block();

        server.onDispose().block();

        System.out.println("Konec");
    }
}
