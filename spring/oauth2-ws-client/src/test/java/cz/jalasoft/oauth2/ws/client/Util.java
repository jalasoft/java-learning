package cz.jalasoft.oauth2.ws.client;

/**
 * @author lastovicka
 */
public class Util {

    public static void main(String[] args) throws OAuth2AuthenticationException {

        OAuth2Authenticator auth = new OAuth2Authenticator();
        OAuth2Client clinet = auth.authenticate();

    }
}
