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
@Configuration
@Profile("local")
public class LokalniConfigurace {

    @Bean
    public AuthorizationCodeResourceDetails githubConfiguration() {
        AuthorizationCodeResourceDetails gg = new AuthorizationCodeResourceDetails();

        gg.setClientId("client1");
        gg.setClientSecret("secret1");
        gg.setAccessTokenUri("http://localhost:8080/oauth/token");
        gg.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
        gg.setGrantType("authorization_code");
        gg.setTokenName("token");
        gg.setClientAuthenticationScheme(AuthenticationScheme.form);
        gg.setAuthenticationScheme(AuthenticationScheme.header);
        //gg.setPreEstablishedRedirectUri("/main");
        //gg.setId("github2");

        return gg;
    }

    @Primary
    @Bean
    public ResourceServerProperties githubResourceeee() {
        ResourceServerProperties props = new ResourceServerProperties();
        props.setUserInfoUri("http://localhost:8080/api/user");
        return props;
    }
}
