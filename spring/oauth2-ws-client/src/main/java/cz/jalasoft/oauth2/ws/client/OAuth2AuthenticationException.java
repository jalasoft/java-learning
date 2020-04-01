package cz.jalasoft.oauth2.ws.client;

/**
 * @author lastovicka
 */
public final class OAuth2AuthenticationException extends Exception {


    public OAuth2AuthenticationException(Exception exc) {
        super(exc);
    }
}
