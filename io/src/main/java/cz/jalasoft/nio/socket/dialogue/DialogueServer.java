package cz.jalasoft.nio.socket.dialogue;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Jan Lastovicka
 * @since 2019-02-22
 */
public final class DialogueServer implements Closeable {

    private volatile Selector selector;
    private ExecutorService executor;

    private final Dialogue dialogue;
    private final ByteBuffer buffer = ByteBuffer.allocate(256);


    public DialogueServer(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public void start(int port) throws IOException {
        executor = Executors.newFixedThreadPool(5);
        selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1", port));
        server.configureBlocking(false);

        server.register(selector, SelectionKey.OP_ACCEPT);

        executor.submit(new Master());
    }

    private void handleNewConnection(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            SelectionKey newKey = client.register(key.selector(), SelectionKey.OP_READ, SelectionKey.OP_WRITE);

            newKey.attach(dialogue.newSession());
            welcomeMessage(newKey);
            askQuestion(newKey);

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private boolean handleSessionRead(SelectionKey key) throws IOException {

        getAnswer(key);
        boolean ifAny = askQuestion(key);

        if (!ifAny) {
            writeFinalDestiny(key);
            return true;
        }

        return false;
    }

    private void welcomeMessage(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        buffer.put("Vitejte v HOROSKOPU!!!\n_____________________\n\n".getBytes());
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
    }

    private void getAnswer(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        channel.read(buffer);
        buffer.flip();

        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        String answer = new String(b).trim();

        DialogueSession session = (DialogueSession) key.attachment();
        session.answer(answer);
        buffer.clear();
    }

    private boolean askQuestion(SelectionKey key) throws IOException {
        DialogueSession session = (DialogueSession) key.attachment();

        if (!session.hasNextQuestion()) {
            return false;
        }

        SocketChannel channel = (SocketChannel) key.channel();
        buffer.put(session.nextQuestion().getBytes());
        buffer.flip();
        channel.write(buffer);
        buffer.clear();

        return true;
    }

    private void writeFinalDestiny(SelectionKey key) throws IOException {
        DialogueSession session = (DialogueSession) key.attachment();

        SocketChannel channel = (SocketChannel) key.channel();

        buffer.put(session.finalResponse().getBytes());
        buffer.flip();

        channel.write(buffer);

        buffer.clear();
    }

    private void handleSessionWrite(SelectionKey key) {
        System.out.println();
    }



    @Override
    public void close() throws IOException {
        try {
            executor.shutdown();
            selector.wakeup();
            selector.close();
        } finally {

            boolean down = false;
            try {
                down = executor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException exc) {

            }

            if (!down) {
                List<Runnable> notExecuted = executor.shutdownNow();

                if (!notExecuted.isEmpty()) {
                    System.out.println("Not finished: " + notExecuted);
                }
            }
        }

        selector = null;
        executor = null;
    }

    private final class Master implements Runnable {

        @Override
        public void run() {
            try {
                while(true) {
                    int number = selector.select();

                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    if (number < 1) {
                        continue;
                    }

                    Set<SelectionKey> keys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = keys.iterator();

                    while(iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        boolean finish = false;

                        if (key.isAcceptable()) {
                            handleNewConnection(key);
                        }

                        if (key.isReadable()) {
                            finish = handleSessionRead(key);
                        }

                        if (key.isWritable()) {
                            handleSessionWrite(key);
                        }

                        iterator.remove();

                        if (finish) {
                            key.channel().close();
                        }
                    }
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}
