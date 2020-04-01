package cz.jalasoft.oauth2.ws.client.receiver;

/**
 * @author lastovicka
 */
public final class AuthenticationCode {

    private final String code;
    private final String state;

    public AuthenticationCode(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String code() {
        return code;
    }

    public String state() {
        return state;
    }

    @Override
    public String toString() {
        return "AuthenticatonCode[" + code() + ",state=" + state + "]";
    }
}
