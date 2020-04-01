package cz.jalasoft.rabbit.clustering;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

	static final String EXCHANGE_NAME = "honzova_exchange";

	public static void main(String[] args) throws IOException, TimeoutException {

		//new BrokerConnector("localhost", 5555, "sms", "user1", "user1").connected(Sender::doSomething);
		new BrokerConnector("sms1.devel.fio.cz", 5672, "sms", "sms", "sms").connected(Sender::doSomething);

	}

	private static void doSomething(Channel channel) throws IOException {

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

		channel.basicPublish(EXCHANGE_NAME, "", null, "Ahoj...".getBytes());
	}
}
