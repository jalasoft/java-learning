package cz.jalasoft.oauth2.ws.client;

import cz.jalasoft.oauth2.ws.client.client.MutableOAuth2ClientContextWrapper;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * @author lastovicka
 */
public final class OAuth2RestTemplateFactory {

    OAuth2RestTemplate createTemplate() {

        AccessTokenRequest request = new DefaultAccessTokenRequest();
        request.setCurrentUri("http://localhost:9090/login/oauth2/code/erste");

        OAuth2ClientContext ctx = new DefaultOAuth2ClientContext(request);
        MutableOAuth2ClientContextWrapper wrapper = new MutableOAuth2ClientContextWrapper();
        wrapper.set(ctx);

        AuthorizationCodeResourceDetails details = authServerProperties();

        return restTemplate(details, wrapper);
    }

    private OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext context) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.private.fio.cz", 8080));
        requestFactory.setProxy(proxy);

        AuthorizationCodeAccessTokenProvider authorizationCodeAccessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        authorizationCodeAccessTokenProvider.setRequestFactory(requestFactory);

        ImplicitAccessTokenProvider implicitAccessTokenProvider = new ImplicitAccessTokenProvider();
        implicitAccessTokenProvider.setRequestFactory(requestFactory);

        AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(
        Arrays.<AccessTokenProvider> asList(authorizationCodeAccessTokenProvider, implicitAccessTokenProvider));

        OAuth2RestTemplate client = new OAuth2RestTemplate(resourceDetails, context);
        client.setAccessTokenProvider(accessTokenProvider);

        client.setRequestFactory(requestFactory);

        return client;
    }

    private AuthorizationCodeResourceDetails authServerProperties() {
        AuthorizationCodeResourceDetails gg = new AuthorizationCodeResourceDetails();

        gg.setClientId("52c1b4e4-f45f-40eb-a34a-048858aed0fa");
        gg.setClientSecret("ec456304-6aba-4c19-9deb-5afc2d65d475");
        gg.setAccessTokenUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/sandbox-idp/token");
        gg.setUserAuthorizationUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/sandbox-idp/auth");
        gg.setGrantType("authorization_code");
        gg.setTokenName("token");
        gg.setScope(Arrays.asList("AIS"));
        gg.setClientAuthenticationScheme(AuthenticationScheme.form);
        gg.setAuthenticationScheme(AuthenticationScheme.header);
        //gg.setPreEstablishedRedirectUri("/main");
        //gg.setId("github2");

        return gg;
    }

}
