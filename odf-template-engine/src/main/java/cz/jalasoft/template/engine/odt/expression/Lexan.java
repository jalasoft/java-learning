package cz.jalasoft.template.engine.odt.expression;

import java.util.Optional;
import java.util.function.Supplier;

public final class Lexan {

	private final Supplier<Optional<Character>> inputSystem;

	public Lexan(Supplier<Optional<Character>> inputSystem) {
		this.inputSystem = inputSystem;
	}


}
