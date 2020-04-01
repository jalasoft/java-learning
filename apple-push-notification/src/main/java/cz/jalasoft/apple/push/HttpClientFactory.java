package cz.jalasoft.apple.push;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.security.KeyStore;

public final class HttpClientFactory {

	public HttpClient newClient() throws Exception {

		ProxySelector proxy = ProxySelector.of(new InetSocketAddress("proxy.private.fio.cz", 8080));

		return HttpClient.newBuilder()
				.sslContext(sslContext())
				.proxy(proxy)
				.build();
	}

	private SSLContext sslContext() throws Exception {
		SSLContext ctx = SSLContext.getInstance("TLSv1.2");
		ctx.init(keyManagers(), trustManagers(), null);
		return ctx;
	}

	private TrustManager[] trustManagers() throws Exception {

		return null;

		/*
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        KeyStore store = KeyStore.getInstance("JKS");

        try(InputStream certStream = getClass().getClassLoader().getResourceAsStream("apns-truststore.jks")) {
			store.load(certStream, "fiofiofio".toCharArray());
		}

        factory.init(store);
        return factory.getTrustManagers();*/
    }

    private KeyManager[] keyManagers() throws Exception {

		KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

		KeyStore store = KeyStore.getInstance("PKCS12");

		try(InputStream certStream = getClass().getClassLoader().getResourceAsStream("apns-dev-keystore.pfx")) {
			store.load(certStream, "fiofiofio".toCharArray());
		}

		factory.init(store, "fiofiofio".toCharArray());

		return factory.getKeyManagers();

	}
}
