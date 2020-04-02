package cz.jalasoft.concurrent;

import java.util.concurrent.*;

/**
 * @author Jan Lastovicka
 * @since 2019-02-19
 */
public class ExtendedExecutor {

    static final class LoggingExecutor<T> extends ThreadPoolExecutor {


        public LoggingExecutor() {
            super(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            FutureTask<T> wrapper = new FutureTask<>(callable) {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    System.out.println("DELA SE CANCEL.....");
                    return super.cancel(mayInterruptIfRunning);
                }
            };

            return wrapper;
        }
    }

    static final class LongTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            try {
                System.out.println("USPAVAM");
                Thread.sleep(10000);
                System.out.println("PROBOUZIM");
            } catch (InterruptedException exc) {
                System.out.println("PRERUSENO....");
            }
            System.out.println("KONEC");
            return "RESULT";
        }
    }

    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("UUUUKLID!!!!!");
            }
        });

        LoggingExecutor executor = new LoggingExecutor();

        Future<String> f = executor.submit(new LongTask());

        Thread.sleep(2000);

        f.cancel(true);

        try {
            String h = f.get();
            System.out.println("VYSLEDEK: " + h);
        } finally {
            executor.shutdownNow();
        }
    }
}
