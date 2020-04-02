package cz.jalasoft.concurrent;

import java.util.concurrent.*;

/**
 * @author Jan Lastovicka
 * @since 2019-02-16
 */
public class ProducerConsumer {

    public static void main(String[] args) {

        new ProducerConsumer().run();

    }

    private final ExecutorService executor;

    ProducerConsumer() {
        executor = Executors.newFixedThreadPool(2);
    }

    void run() {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(11);

        Producer producer = new Producer(queue, 10);
        Consumer consumer = new Consumer(queue);

        executor.submit(producer);
        executor.submit(consumer);

        /*
        try {
            producer.await();
        } catch (InterruptedException exc) {
            Thread.interrupted();
        }*/

        waitAndShutdown(15);
    }

    private void waitAndShutdown(int seconds) {
        executor.shutdown();

        try {
            boolean terminated = executor.awaitTermination(seconds, TimeUnit.SECONDS);

            if (!terminated) {
                executor.shutdownNow();
                System.out.println("POOL FORCELY SHUT DOWN");
            }

            System.out.println("POOL SHUT DOWN");
        } catch (InterruptedException exc) {
            executor.shutdownNow();
            System.out.printf("POOL EVENTUALLY SHUT DOWN");
        }
    }

    private static final class Producer implements Runnable {

        private final BlockingQueue<Message> queue;
        private final int number;

        Producer(BlockingQueue<Message> queue, int number) {
            this.queue = queue;
            this.number = number;
        }

        @Override
        public void run() {

            long l = 0;
            for(;;) {

                try {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("PRODUCER: sending poison pill");
                        sendPoisonPill();
                        Thread.interrupted();
                        return;
                    }


                    Message message = new Message("Message: " + l++);
                    System.out.println("PRODUCER: sending message: " + message.value());
                    queue.put(message);

                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    try {
                        sendPoisonPill();
                    } catch (InterruptedException exc2) {
                        break;
                    }
                    break;
                }
            }

            /*
            try {
                queue.put(Message.POISON_PILL);
            } catch (InterruptedException exc) {
                System.out.println("PRODUCER: interrupted");
                return;
            }*/
        }

        private void sendPoisonPill() throws InterruptedException {
            queue.put(Message.POISON_PILL);
        }
    }

    private static final class Consumer implements Runnable {

        private final BlockingQueue<Message> queue;

        public Consumer(BlockingQueue<Message> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            while(true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("CONSUMER: interrupter, closing.");
                        return;
                    }

                    Message message = queue.take();

                    if (message == Message.POISON_PILL) {
                        System.out.println("CONSUMER: poison pill obtained, closing.");
                        return;
                    }

                    System.out.println("CONSUMER: obtained message: " + message.value());
                } catch (InterruptedException exc) {
                    System.out.println("CONSUMER: interrupter, closing.");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static final class Message {

        static final Message POISON_PILL = new Message("KILL");

        private final String value;

        public Message(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
