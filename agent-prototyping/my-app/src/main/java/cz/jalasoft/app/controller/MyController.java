package cz.jalasoft.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/hello")
    public String hello() {

        /*
        Arrays.stream(getClass().getDeclaredMethods()).forEach(m -> {
            System.out.println("Mam metodu " + m.getName());
            String ann = Arrays.stream(m.getDeclaredAnnotations()).map(Object::toString).collect(Collectors.joining(", "));
            System.out.println("Anotace: " + ann);
         });

        ClassLoader l = getClass().getClassLoader();


        System.out.println("STACK:");
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        Arrays.stream(elements).forEach(elm -> {
            System.out.println(elm.getClassName() + "." + elm.getMethodName() + "(...)");
        });

        System.out.println();
        System.out.println("Classloader: " + l);
        */
        try {
            Thread.sleep(500);
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }

        //System.out.println("Bumbo!!!");*/

        return "Hellow World!!!!";
    }
}
