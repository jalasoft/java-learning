package cz.jalasoft.concurrent.reactive;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Jan Lastovicka
 * @since 2019-03-04
 */
public final class ReactiveWatchService implements Flow.Publisher<WatchEvent<?>>, Closeable {

    public static void main(String[] args) throws IOException, InterruptedException {

        try(ReactiveWatchService service = new ReactiveWatchService()) {

            Path file1 = Paths.get(System.getProperty("user.home"));
            service.register(file1, StandardWatchEventKinds.ENTRY_MODIFY);

            service.subscribe(new WatchEventSubscriber("Jirka"));
            service.subscribe(new WatchEventSubscriber("Pavel"));

            Thread.sleep(100_000);
        }
    }

    //-----------------------------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------------------------

    private final WatchService watchService;

    private final ExecutorService executor;
    private volatile SubmissionPublisher<WatchEvent<?>> publisher;

    public ReactiveWatchService() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.executor = Executors.newCachedThreadPool();
        this.publisher = new SubmissionPublisher<>(executor, 20);

        this.executor.submit(new MasterTask());
    }

    public void register(Path path, WatchEvent.Kind... kinds) throws IOException {
        path.register(watchService, kinds);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super WatchEvent<?>> subscriber) {
        publisher.subscribe(subscriber);
    }

    @Override
    public void close() throws IOException {
        try {
            publisher.close();
        } finally {
            try {
                watchService.close();
            } finally {
                executor.shutdown();
            }
        }
    }

    //---------------------------------------------------------------------------
    //INSTANCE SCOPE
    //---------------------------------------------------------------------------

    private final class MasterTask implements Runnable {

        @Override
        public void run() {

            while(true) {
                try {
                    WatchKey key = watchService.take();

                    List<WatchEvent<?>> events = key.pollEvents();
                    events.forEach(e -> {
                        ReactiveWatchService.this.publisher.submit(e);
                    });
                    key.reset();

                } catch (InterruptedException exc) {
                    return;
                }
            }
        }
    }
}
