package cz.jalasoft.oauth2.client.config;

import cz.jalasoft.oauth2.client.config.beans.OAuth2TargetUrl;
import cz.jalasoft.oauth2.client.config.beans.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

/**
 * @author lastovicka
 */
@Profile("google")
@Configuration
public class GoogleConnectionConfig {

    @Bean
    public OAuth2TargetUrl targetUrl() {
        OAuth2TargetUrl url = new OAuth2TargetUrl();
        url.setValue("/login/oauth2/code/google");
        return url;
    }

    @Bean
    public AuthorizationCodeResourceDetails ssoDetails() {
        AuthorizationCodeResourceDetails props = new AuthorizationCodeResourceDetails();

        props.setClientId("483225856246-tnurtdql99gcsbcn5l68u6lq97vdm5rg.apps.googleusercontent.com");
        props.setClientSecret("***");
        props.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        props.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        //props.setAuthenticationScheme(AuthenticationScheme.form);
        props.setGrantType("authorization_code");
        //props.setTokenName("oauth_token");
        props.setClientAuthenticationScheme(AuthenticationScheme.form);
        props.setAuthenticationScheme(AuthenticationScheme.query);
        props.setScope(Arrays.asList("profile"));
        return props;
    }

    @Bean
    public ResourceServerProperties ssoUserDetails() {
        ResourceServerProperties props = new ResourceServerProperties();

        props.setUserInfoUri("https://www.googleapis.com/userinfo/v2/me");

        return props;
    }
}
