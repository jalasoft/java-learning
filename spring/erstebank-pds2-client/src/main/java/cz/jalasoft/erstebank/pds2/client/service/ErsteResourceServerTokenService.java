package cz.jalasoft.erstebank.pds2.client.service;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lastovicka
 */
public class ErsteResourceServerTokenService implements ResourceServerTokenServices {

	private final String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	//private final OAuth2RestTemplate restTemplate;
	//private final String userInfoEndpointUrl;
	private final String clientId;
	//private final String webApiKey;

	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
	private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

	public ErsteResourceServerTokenService(String clientId) {
		this.clientId = clientId;
	}

	/*
    public ErsteResourceServerTokenService(OAuth2RestTemplate restTemplate, String userInfoEndpointUrl, String clientId, String webApiKey) {
        this.restTemplate = restTemplate;
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.clientId = clientId;
        this.webApiKey = webApiKey;
    }*/

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		//Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
		Map<String, Object> map = new HashMap<>();
		map.put("name", "hardcoded_name");

		return extractAuthentication(map);
	}


	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = this.authoritiesExtractor
				.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
				null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, "N/A", authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}

	protected Object getPrincipal(Map<String, Object> map) {
		Object principal = this.principalExtractor.extractPrincipal(map);
		return (principal == null ? "unknown" : principal);
	}

    /*
    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();

        if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
            token.setTokenType(this.tokenType);
            restTemplate.getOAuth2ClientContext().setAccessToken(token);
        }

        Map<String, Object> result = getMap(userInfoEndpointUrl, accessToken);

        return null;
    }

    private Map<String, Object> getMap(String path, String accessToken) {
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
					.getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
						accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}

			RequestCallback cb = new UserInfoRequestCallback(webApiKey);
			ResponseEntity<Map>  response = restTemplate.execute(path, HttpMethod.GET, cb, new MyResponseExtractor<>(Map.class));

			return response.getBody();
		}
		catch (Exception ex) {
			return Collections.<String, Object>singletonMap("error",
					"Could not fetch user details");
		}
	}
	/*
	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = this.authoritiesExtractor.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
				null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, "N/A", authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}*/

    /*
    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException();
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
	}*/
}
