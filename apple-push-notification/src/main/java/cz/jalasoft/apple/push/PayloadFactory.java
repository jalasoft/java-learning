package cz.jalasoft.apple.push;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

final class PayloadFactory {

	static Map<String, Object> payload() {
		Map<String, Object> payload = new HashMap<>();

		payload.put("aps", buildAps());
		return payload;
	}

	private static Map<String, Object> buildAps() {

			return buildAlertAps();
	}

	private static Map<String, Object> badgeAps() {
		Map<String, Object> payload = new HashMap<>();

		payload.put("badge", "Pozdrav od Honzalese");

		return payload;
	}

	private static Map<String, Object> buildAlertAps() {
		Map<String, Object> payload = new HashMap<>();

		payload.put("title", "Fiiio je tu!!!!");
		payload.put("subtitle", "subtajtl");
		payload.put("body", "Pozdrav od Honzalese");

		Map<String, Object> root = new HashMap<>();
		root.put("alert", payload);
		return root;
	}
}
