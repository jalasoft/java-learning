package cz.jalasoft.oauth2.client.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

/**
 * @author Jan Lastovicka
 * @since 2019-04-02
 */
@Profile("github")
@Configuration
public class GithubConfigurace {

    @Bean
    public AuthorizationCodeResourceDetails githubConfiguration() {
        AuthorizationCodeResourceDetails gg = new AuthorizationCodeResourceDetails();

        gg.setClientId("810af70f30bb1d011bb3");
        gg.setClientSecret("e494253ff0603099efc03890d6082af3c9f****");
        gg.setAccessTokenUri("https://github.com/login/oauth/accesstoken");
        gg.setUserAuthorizationUri("https://github.com/login/oauth/authorize");
        gg.setGrantType("authorization_code");
        gg.setTokenName("oauth_token");
        gg.setClientAuthenticationScheme(AuthenticationScheme.form);
        gg.setAuthenticationScheme(AuthenticationScheme.query);
        //gg.setPreEstablishedRedirectUri("/main");
        //gg.setId("github2");

        return gg;
    }

    @Primary
    @Bean
    public ResourceServerProperties githubResourceeee() {
        ResourceServerProperties props = new ResourceServerProperties();
        props.setUserInfoUri("https://api.github.com/user");
        return props;
    }
}
