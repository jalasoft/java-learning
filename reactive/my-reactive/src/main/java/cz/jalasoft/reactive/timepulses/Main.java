package cz.jalasoft.reactive.timepulses;

import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Main {

	public static void main(String[] args) {

		TimePulseGenerator.generate().sample(Duration.ofSeconds(2)).publishOn(Schedulers.parallel()).subscribe(p -> System.out.println(p.asString()));

	}
}
