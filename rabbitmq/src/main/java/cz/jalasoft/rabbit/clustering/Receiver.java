package cz.jalasoft.rabbit.clustering;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class Receiver {

	public static void main(String[] args) throws IOException, TimeoutException {

		//new BrokerConnector("localhost", 6666, "sms", "user1", "user1").connected(Receiver::consume);
		new BrokerConnector("sms2.devel.fio.cz", 5672, "sms", "sms", "sms").connected(Receiver::consume);
	}

	private static void consume(Channel channel) throws IOException {

		channel.exchangeDeclare(Sender.EXCHANGE_NAME, "fanout", true);

		String genericQueueName = channel.queueDeclare().getQueue();

		channel.queueBind(genericQueueName, Sender.EXCHANGE_NAME, "");

		while (true) {
			channel.basicConsume(genericQueueName, true, Receiver::handleDelivery, Receiver::handleCancel);
		}
	}

	private static void handleDelivery(String tag, Delivery delivery) throws IOException {
		byte[] body = delivery.getBody();

		String message = new String(body);

		System.out.println("PRISLO: " + message);
	}

	private static void handleCancel(String tag) throws IOException {
		System.out.println("CANCEL");
	}
}
