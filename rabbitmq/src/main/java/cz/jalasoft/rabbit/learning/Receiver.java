package cz.jalasoft.rabbit.learning;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class Receiver {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);

        //CountDownLatch latch = new CountDownLatch(1);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.basicQos(1);

        channel.basicConsume("pokus", false, new DeliverCallback() {

            int counter = 0;

            @Override
            public void handle(String s, Delivery delivery) throws IOException {

                String msg = new String(delivery.getBody());

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
               // if (counter++ % 2 == 1) {
               //     System.out.println(msg + " - NACK");
               //     channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
               // } else {
                    System.out.println(msg + " - ACK");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                //}
            }
        }, str -> {});

    }
}
