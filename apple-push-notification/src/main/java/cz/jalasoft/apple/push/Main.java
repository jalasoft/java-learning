package cz.jalasoft.apple.push;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

public class Main {

	private static final String HEADER_APNS_ID = "apns-id";
	private static final String HEADER_APNS_PUSH_TYPE = "apns-push_type";
	private static final String HEADER_APNS_EXPIRATION = "apns-expiration";
	private static final String HEADER_APNS_PRIORITY = "apns-priority";
	private static final String HEADER_APNS_TOPIC = "apns-topic";

	public static void main(String[] args) throws Exception {

		HttpClientFactory factory = new HttpClientFactory();

		HttpClient client = factory.newClient();

		Map<String, Object> payloadMap = PayloadFactory.payload();

		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, payloadMap);
		String payload = writer.toString();


		URI uri = URI.create("https://api.push.apple.com/3/device/83fff58fc917062946209b7668f2edcfde7f5d442262b596cfcc92d4f7*****");

		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(payload))
				.uri(uri)
				.header(HEADER_APNS_ID, UUID.randomUUID().toString())
				.header(HEADER_APNS_PUSH_TYPE, "alert")
				.header(HEADER_APNS_EXPIRATION, "0")
				.header(HEADER_APNS_PRIORITY, "10")
				//.header(HEADER_APNS_TOPIC, "cz.fio.FioSB2skT")
				.header(HEADER_APNS_TOPIC, "***")
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println("Status: " + response.statusCode());
		System.out.println("Body:" + response.body());
	}
}
