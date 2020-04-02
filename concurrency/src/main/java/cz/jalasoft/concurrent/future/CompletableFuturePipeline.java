package cz.jalasoft.concurrent.future;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jan Lastovicka
 * @since 2019-03-02
 */
public class CompletableFuturePipeline {

    public static void main(String[] args) {

        CompletableFuture<String> async1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(3000);
                    return LocalDate.now();
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
        }).handle((d, ex) -> {
           if (ex != null) {
               return "je to hnuj";
           }
           return "Mam datum: " + d;
        });

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Ahoj";
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }).thenApply(str -> {
            return str.length();
            //throw new RuntimeException("bleeee");
        }).exceptionally(th -> {
            System.out.println("Dostal jsem vyjimku: " + th.getMessage());
            return 0;
        }).thenCombine(async1, (i, s) -> {
            return "Zkombinovano, cislo " + i + " s " + s;
        }).thenAccept(i -> {
            System.out.println("Ve finale jsem dostal " + i);
        }).join();
    }
}
