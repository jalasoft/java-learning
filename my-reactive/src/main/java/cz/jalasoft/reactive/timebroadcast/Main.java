package cz.jalasoft.reactive.timebroadcast;

import reactor.core.publisher.ConnectableFlux;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

public class Main {

	public static void main(String[] args) throws Exception {

		/*
		ConnectableFlux<LocalTime> broadcast = TimeBroadcast.broadcast().publish();

		broadcast.subscribe(v -> System.out.println("S1: " + v));
		broadcast.connect();

		Thread.sleep(2_000);

		broadcast.subscribe(v -> System.out.println("S2: " + v));

		Thread.sleep(10_000);*/

		CountDownLatch l = new CountDownLatch(1);

		TimeBroadcast.broadcast().subscribe(v -> {}, e -> {}, () -> l.countDown());

		l.await();

		System.out.println("HOTOVO");
	}
}
