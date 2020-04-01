package cz.jalasoft.reactive.timebroadcast;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.time.LocalTime;

public final class TimeBroadcast {

	public static Flux<LocalTime> broadcast() {

		return Flux.range(1, 10)
				.doOnNext(v -> System.out.println("Zpracovavam " + v + " v " + Thread.currentThread().getName()))
				.map(i -> LocalTime.now())
				.publishOn(Schedulers.parallel())
				.handle((LocalTime t, SynchronousSink<LocalTime> s) -> {
					try {
						Thread.sleep(1000);
						s.next(t);
					} catch (InterruptedException exc) {
						s.error(exc);
					}
				})
				.doOnNext(v -> System.out.println("Zpracovavam " + v + " v " + Thread.currentThread().getName()));
	}
}
