package cz.jalasoft.reactor.core.learning;

import reactor.core.publisher.Flux;

/**
 * @author Jan Lastovicka
 * @since 2019-09-22
 */
public class FluxGenerate {

    public static void main(String[] args) {

        flux().log().subscribe(System.out::println, th -> System.out.println(th), () -> System.out.printf("Hotovo"));

    }

    private static Flux<Integer> flux() {
        return Flux.generate(() -> 0, (n, sink) -> {
            if (n > 25) {
                sink.complete();
                return n;
            }
            sink.next(n);
            return ++n;
        });
    }
}
