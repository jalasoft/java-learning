package cz.jalasoft.oauth2.client.config;

import cz.jalasoft.oauth2.client.config.beans.OAuth2TargetUrl;
import cz.jalasoft.oauth2.client.config.beans.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

/**
 * @author lastovicka
 */
@Profile("github")
@Configuration
public class GithubConnectionConfig {

    @Bean
    public OAuth2TargetUrl targetUrl() {
        OAuth2TargetUrl url = new OAuth2TargetUrl();
        url.setValue("/login/oauth2/code/github");
        return url;
    }

    @Bean
    public AuthorizationCodeResourceDetails ssoDetails() {
        AuthorizationCodeResourceDetails props = new AuthorizationCodeResourceDetails();

        props.setClientId("810af70f30bb1d011bb3");
        props.setClientSecret("e494253ff0603099efc03890d6082af3c9fe328c");
        props.setUserAuthorizationUri("https://github.com/login/oauth/authorize");
        props.setAccessTokenUri("https://github.com/login/oauth/access_token");
        //props.setAuthenticationScheme(AuthenticationScheme.form);
        props.setGrantType("authorization_code");
        //props.setTokenName("oauth_token");
        props.setClientAuthenticationScheme(AuthenticationScheme.form);
        props.setAuthenticationScheme(AuthenticationScheme.query);
        return props;
    }

    @Bean
    public ResourceServerProperties ssoUserDetails() {
        ResourceServerProperties props = new ResourceServerProperties();

        props.setUserInfoUri("https://api.github.com/user");

        return props;
    }
}
