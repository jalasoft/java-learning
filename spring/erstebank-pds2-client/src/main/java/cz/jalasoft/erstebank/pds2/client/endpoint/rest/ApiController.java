package cz.jalasoft.erstebank.pds2.client.endpoint.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.Map;

/**
 * @author lastovicka
 */
@RestController
public class ApiController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    private String webApiKey = "4cba8e37-66ef-4ab9-bacb-21f19c8529bd";

    @GetMapping("/accounts")
    public String accounts() {

        //ResponseEntity<Map>  response = restTemplate.getForEntity("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/account-information/my/accounts", Map.class);

        String url = "https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/account-information/my/accounts";
        ResponseEntity<Map> result = restTemplate.execute(url, HttpMethod.GET, new UserInfoRequestCallback(webApiKey), new MyResponseExtractor<>(Map.class));

        String string = result.getBody().toString();
        return string;
    }

    private static final class UserInfoRequestCallback implements RequestCallback {

    	private final String webApiKey;

		public UserInfoRequestCallback(String webApiKey) {
			this.webApiKey = webApiKey;
		}

		@Override
		public void doWithRequest(ClientHttpRequest request) throws IOException {
			request.getHeaders().add("WEB-API-key", webApiKey);
		}
	}

	private final class MyResponseExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

    	private final Class<T> responseType;

		public MyResponseExtractor(Class<T> responseType) {
			this.responseType = responseType;
		}

		@Nullable
		@Override
		public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {

			HttpMessageConverterExtractor<T> delegate = new HttpMessageConverterExtractor<T>(responseType, restTemplate.getMessageConverters());

			return ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).body(delegate.extractData(response));
		}
	}

}
