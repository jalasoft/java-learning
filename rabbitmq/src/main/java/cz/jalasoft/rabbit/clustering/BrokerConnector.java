package cz.jalasoft.rabbit.clustering;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class BrokerConnector {

	private final String server;
	private final int port;
	private final String virtualHost;
	private final String username;
	private final String password;

	public BrokerConnector(String server, int port, String virtualHost, String username, String password) {
		this.server = server;
		this.port = port;
		this.virtualHost = virtualHost;
		this.username = username;
		this.password = password;
	}

	void connected(IOConsumer consumer) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(server);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setVirtualHost(virtualHost);

		try(Connection connection = factory.newConnection();
			Channel channel = connection.createChannel()) {
			consumer.accept(channel);
		}
	}

	public interface IOConsumer {

		void accept(Channel ch) throws IOException;
	}
}
