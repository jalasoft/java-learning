package cz.jalasoft.concurrent.lock;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jan Lastovicka
 * @since 2019-02-27
 */
public class ConditionQueueWithFileReading implements Closeable {

    public static void main(String[] args) throws Exception {

        Path file = Paths.get(System.getProperty("user.home"), "soubor.txt");
        try(ConditionQueueWithFileReading a = new ConditionQueueWithFileReading(file)) {
            a.start();

            Thread.sleep(30_000);
        }
    }

    private final ExecutorService executor;
    private final Path file;

    private final Lock lock;
    private final Condition condition;
    private Collection<String> lines;

    public ConditionQueueWithFileReading(Path file) {
        this.file = file;
        this.executor = Executors.newFixedThreadPool(2);
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    public void start() {
        this.executor.submit(new ContentReaderTask());
        this.executor.submit(new FileReaderTask());
    }

    private Collection<String> awaitLinesRefresh() throws InterruptedException {

        lock.lockInterruptibly();
        try {
            condition.await();
            return lines;
        } finally {
            lock.unlock();
        }
    }

    private void refreshLines(Collection<String> lines) throws InterruptedException {

        lock.lockInterruptibly();
        try {
            this.lines = lines;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws IOException {
        this.executor.shutdown();

        boolean down = false;

        try {
            down = this.executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException exc) {

        }

        if (!down) {
            this.executor.shutdownNow();
        }
    }

    private final class FileReaderTask implements Runnable {
        @Override
        public void run() {

            while(true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    return;
                }

                Collection<String> lines;
                try {
                    lines = Files.readAllLines(file);
                    refreshLines(lines);
                } catch (InterruptedException exc) {
                    return;
                } catch (IOException exc) {
                    exc.printStackTrace();
                    return;
                }
            }

        }
    }

    private final class ContentReaderTask implements Runnable {
        @Override
        public void run() {

            while(true) {
                try {
                    Collection<String> lines = awaitLinesRefresh();

                    System.out.println("LINES");
                    System.out.println(lines);
                    System.out.println("_____________\n");
                } catch (InterruptedException exc) {
                    return;
                }
            }
        }
    }
}
