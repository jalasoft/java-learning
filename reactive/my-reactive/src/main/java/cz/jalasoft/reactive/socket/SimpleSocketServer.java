package cz.jalasoft.reactive.socket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Jan Lastovicka
 * @since 2019-02-19
 */
public final class SimpleSocketServer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private volatile ServerSocket server;
    private volatile FluxSink<Socket> sink;


    public Flux<Socket> start(int port) throws IOException {
        this.server = new ServerSocket(port);
        this.executor.submit(new MainThread());

        return Flux.create(sink -> {
            SimpleSocketServer.this.sink = sink;
        });
    }

    public void shutdown() throws IOException {
        server.close();

        executor.shutdown();

        boolean down = false;
        try {
            down = executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException exc) {

        } finally {
            if (!down) {
                executor.shutdownNow();
            }
        }
    }

    private final class MainThread implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    Socket socket = server.accept();
                    sink.next(socket);
                    //executor.submit(() -> sink.next(socket));
                } catch (SocketException exc) {
                    if ("Socket is closed".equals(exc.getMessage())) {
                        sink.complete();
                        return;
                    }
                    exc.printStackTrace();
                } catch (IOException exc) {
                    exc.printStackTrace();
                    return;
                } finally {
                    sink.complete();
                }
            }
        }
    }

    public interface SocketHandler {

        void handle(Socket socket) throws IOException;
    }
}
