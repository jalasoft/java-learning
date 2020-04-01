package cz.jalasoft.app;

import java.lang.instrument.Instrumentation;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class AgentClass {

    public static void premain(String agentArgs, Instrumentation inst) {

        Arguments args = Arguments.parse(agentArgs);
        Predicate<String> classNamePredicate = classNamePredicate(args);

        inst.addTransformer(new TimeMeasuringClassFileTransformer(classNamePredicate));
    }

    private static Predicate<String> classNamePredicate(Arguments arguments) {

        String regex = arguments.when("package", p -> "^" + p.replace(".", "\\.").replace("*", ".*") + "$").orElse(".*");

        Pattern pattern = Pattern.compile(regex);

        System.out.println("Pattern: " + pattern);

        return p -> pattern.matcher(p).matches();
    }


    public static void main(String[] args) {
        String h = "fgfdg";
        ClassLoader l = h.getClass().getClassLoader();

        System.out.println(l);

        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
