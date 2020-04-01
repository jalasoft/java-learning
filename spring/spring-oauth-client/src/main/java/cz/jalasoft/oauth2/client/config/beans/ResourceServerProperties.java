package cz.jalasoft.oauth2.client.config.beans;

/**
 * @author lastovicka
 */
public final class ResourceServerProperties {

    private String userInfoUri;

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }
}
