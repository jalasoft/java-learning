package cz.jalasoft.reactive.timepulses;

import java.time.LocalTime;

public final class Pulse {

	public static Pulse now() {
		return new Pulse(LocalTime.now());
	}

	private final LocalTime value;

	private Pulse(LocalTime value) {
		this.value = value;
	}

	public String asString() {
		return value.toString();
	}
}
