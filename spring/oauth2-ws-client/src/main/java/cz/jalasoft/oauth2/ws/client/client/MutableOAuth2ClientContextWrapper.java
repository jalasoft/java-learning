package cz.jalasoft.oauth2.ws.client.client;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author lastovicka
 */
public final class MutableOAuth2ClientContextWrapper implements OAuth2ClientContext {

    private OAuth2ClientContext decorated;

    public void set(OAuth2ClientContext context) {
        this.decorated = context;
    }

    @Override
    public OAuth2AccessToken getAccessToken() {
        return decorated.getAccessToken();
    }

    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {
        decorated.setAccessToken(accessToken);
    }

    @Override
    public AccessTokenRequest getAccessTokenRequest() {
        return decorated.getAccessTokenRequest();
    }

    @Override
    public void setPreservedState(String stateKey, Object preservedState) {
        decorated.setPreservedState(stateKey, preservedState);
    }

    @Override
    public Object removePreservedState(String stateKey) {
        return decorated.removePreservedState(stateKey);
    }
}
