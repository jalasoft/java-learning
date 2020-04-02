package cz.jalasoft.reactor.core.learning;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author Jan Lastovicka
 * @since 23/09/2019
 */
public class HotMillis {

    public static void main(String[] args) throws InterruptedException {

        Flux<Integer> ints = Flux.generate(() -> 0, (i, sink) -> {
            sink.next(i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException exc) {
                sink.error(exc);
            }
           return ++i;
        });

        ints.log().subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("1 - " + i));

        Thread.sleep(5000);

        ints.log().subscribe(i -> System.out.println("2 - " + i));

        Thread.sleep(10000);

       // System.out.println("konec");
    }
}
