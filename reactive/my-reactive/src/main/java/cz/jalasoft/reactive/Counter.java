package cz.jalasoft.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

public class Counter {

	public static void main(String[] args) throws Exception {

		CountDownLatch latch = new CountDownLatch(1);

		Flux.range(0, 100).map(i -> {
			if (i == 15) {
				throw new RuntimeException("Chybicka se vloudila.");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException exc) {}
			return i;
		}).publishOn(Schedulers.parallel()).subscribe(c -> System.out.println(c), e -> {System.out.println(e.toString()); latch.countDown();}, () -> latch.countDown());

		latch.await();
		System.out.println("Koncim");
	}
}
