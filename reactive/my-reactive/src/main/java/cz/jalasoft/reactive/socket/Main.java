package cz.jalasoft.reactive.socket;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Jan Lastovicka
 * @since 2019-02-20
 */
public class Main {


    public static void main(String[] args) throws Exception {

        SimpleSocketServer server = new SimpleSocketServer();
        Flux<Socket> flux = server.start(8888);

        flux.subscribe(Main::handleSocket);

        Thread.sleep(100_000);

        server.shutdown();
    }

    private static void handleSocket(Socket socket) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String line;
            while ((line = reader.readLine()) != null) {
                if ("exit".equals(line)) {
                    break;
                }
                System.out.println("Neco mi prislo: " + line);
                writer.println("Diky....");
                writer.flush();

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        } finally {
            System.out.println("SOCKET SKONCIL");
        }
    }
}


