package cz.jalasoft.concurrent;

import java.util.concurrent.*;

/**
 * @author Jan Lastovicka
 * @since 2019-02-25
 */
public class DeadlockExample {

    public static void main(String[] args) throws Exception {
        DeadlockExample ex = new DeadlockExample();
        ex.doIt();
    }

    //private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    public void doIt() throws InterruptedException, ExecutionException {
        System.out.println("Submituju master");
        Future<?> f = executor.submit(new MasterTask(executor));
        f.get();
        System.out.println("Master skoncil");
    }


    private final class MasterTask implements Runnable {

        private final ExecutorService executor;

        public MasterTask(ExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public void run() {
            System.out.println("MASTER: Submituju slave");
            Future<String> g = executor.submit(new SlaveTask());

            try {
                String h = g.get();
                System.out.println("MASTER: mam vysledek: " + h);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private final class SlaveTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "Bleeeee";
        }
    }
}
