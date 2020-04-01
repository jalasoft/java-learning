package cz.jalasoft.rabbit.learning;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> params = new HashMap<>();
        params.put("x-max-length", 3);
        params.put("x-overflow", "reject-publish");
        params.put("x-dead-letter-exchange", "odpadlici");


        AMQP.Queue.DeclareOk r = channel.queueDeclare("", true, false, false, params);
        String name = r.getQueue();

        //----------------------------
        channel.exchangeDeclare("odpadlici", "fanout");
        String dlxQueue = channel.queueDeclare().getQueue();
        channel.queueBind(dlxQueue, "odpadlici", "");
        channel.basicConsume(dlxQueue, true, (tag, message) -> {
            String body = new String(message.getBody());
            System.out.println("Dosal jsem odpadlika: " + body);
        }, str -> {
        });
        //---------------------------

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ACK pro " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("NACK pro " + deliveryTag);
            }
        });

        channel.basicPublish("", name, null, "cau".getBytes());
        channel.basicPublish("", name, null, "ahoj".getBytes());
        channel.basicPublish("", name, null, "nazar".getBytes());
        channel.basicPublish("", name, null, "zdravim".getBytes());
        channel.basicPublish("", name, null, "cus".getBytes());

        System.out.println(channel.queueDeclarePassive(name).getMessageCount());
    }
}
