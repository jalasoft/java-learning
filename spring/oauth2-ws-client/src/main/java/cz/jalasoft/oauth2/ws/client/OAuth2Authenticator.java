package cz.jalasoft.oauth2.ws.client;

import cz.jalasoft.oauth2.ws.client.client.MutableOAuth2ClientContextWrapper;
import cz.jalasoft.oauth2.ws.client.receiver.AuthenticationCode;
import cz.jalasoft.oauth2.ws.client.receiver.AuthorizationCodeReceiver;
import cz.jalasoft.oauth2.ws.client.login.WebLoginAuthenticator;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lastovicka
 */
public final class OAuth2Authenticator {

    public OAuth2Client authenticate() throws OAuth2AuthenticationException {

        OAuth2RestTemplateFactory factory = new OAuth2RestTemplateFactory();
        OAuth2RestTemplate template = factory.createTemplate();

        AuthorizationCodeReceiver server = new AuthorizationCodeReceiver();
        server.start();

        AuthenticationCode code;
        try {
            code = server.awaitAuthentication();
            new WebLoginAuthenticator().fillLoginAndSubmit(template);
        } finally {
            server.stop();
        }

        Map<String, String[]> params = new HashMap<>();
        params.put("code", new String[] { code.code()});
        params.put("state", new String[] { code.state()});

        AccessTokenRequest request = new DefaultAccessTokenRequest(params);
        OAuth2ClientContext context = new DefaultOAuth2ClientContext(request);

        MutableOAuth2ClientContextWrapper wrapper = (MutableOAuth2ClientContextWrapper) template.getOAuth2ClientContext();
        wrapper.set(context);

        OAuth2AccessToken token = template.getAccessToken();

        return null;
    }

    private void authenticateSafely() throws OAuth2AuthenticationException {

    }


}
