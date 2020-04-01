package cz.jalasoft.oauth2.client.config;

import cz.jalasoft.oauth2.client.config.beans.OAuth2TargetUrl;
import cz.jalasoft.oauth2.client.config.beans.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

/**
 * @author lastovicka
 */
@Profile("local")
@Configuration
public class LocalConnectionConfig {

    @Bean
    public OAuth2TargetUrl targetUrl() {
        OAuth2TargetUrl url = new OAuth2TargetUrl();
        url.setValue("/login/oauth2/code/local");
        return url;
    }

    @Bean
    public AuthorizationCodeResourceDetails ssoDetails() {
        AuthorizationCodeResourceDetails props = new AuthorizationCodeResourceDetails();

        props.setClientId("client1");
        props.setClientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("pwd1"));
        //props.setClientSecret("pwd1");
        props.setUserAuthorizationUri("http://localhost:9999/oauth/authorize");
        props.setAccessTokenUri("http://localhost:9999/oauth/token/");
        //props.setAuthenticationScheme(AuthenticationScheme.form);
        props.setGrantType("authorization_code");
        props.setClientAuthenticationScheme(AuthenticationScheme.form);
        props.setAuthenticationScheme(AuthenticationScheme.query);
        //props.setTokenName("oauth_token");
        //props.setClientAuthenticationScheme(AuthenticationScheme.form);
        //props.setAuthenticationScheme(AuthenticationScheme.query);
        props.setScope(Arrays.asList("profile"));
        return props;
    }

    @Bean
    public ResourceServerProperties ssoUserDetails() {
        ResourceServerProperties props = new ResourceServerProperties();

        props.setUserInfoUri("http://localhost:9999/userinfo/me");

        return props;
    }
}
