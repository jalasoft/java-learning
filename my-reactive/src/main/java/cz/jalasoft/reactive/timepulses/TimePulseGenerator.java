package cz.jalasoft.reactive.timepulses;

import reactor.core.publisher.Flux;

public final class TimePulseGenerator {

	static Flux<Pulse> generate() {
		return Flux.create(sink -> {
			while(true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException exc) {
					sink.error(exc);
				}

				sink.next(Pulse.now());
			}
		});
	}

}
