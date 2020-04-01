package cz.jalasoft.app;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public final class Arguments {

    static Arguments parse(String string) {
        Map<String, String> args = stream(string.split(","))
                .collect(toMap(p -> p.split(":")[0], p -> p.split(":")[1]));

        return new Arguments(args);
    }

    //-----------------------------------------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------------------------------------

    private final Map<String, String> values;

    private Arguments(Map<String, String> values) {
        this.values = values;
    }

    boolean has(String name) {
        return values.containsKey(name);
    }

    String get(String name) {
        if (!has(name)) {
            throw new IllegalStateException("No argument '" + name + "' available.");
        }

        return this.values.get(name);
    }

    void when(String name, Consumer<String>consumer) {
        if (!has(name)) {
            return;
        }

        consumer.accept(get(name));
    }

    <T> Optional<T> when(String name, Function<String, T> transformer) {
        if (!has(name)) {
            return Optional.empty();
        }

        return Optional.of(transformer.apply(get(name)));
    }
}
