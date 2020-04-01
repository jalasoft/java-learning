package cz.jalasoft.erstebank.pds2.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

/**
 * @author lastovicka
 */
@Configuration
public class ErsteConnectionConfig {

    @Bean
    public AuthorizationCodeResourceDetails authServerProperties() {
        AuthorizationCodeResourceDetails gg = new AuthorizationCodeResourceDetails();

        gg.setClientId("52c1b4e4-f45f-40eb-a34a-048858aed0fa");
        gg.setClientSecret("ec456304-6aba-4c19-9deb-5afc2d65d475");
        gg.setAccessTokenUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/sandbox-idp/token");
        gg.setUserAuthorizationUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/sandbox-idp/auth");
        gg.setGrantType("authorization_code");
        gg.setTokenName("token");
        gg.setScope(Arrays.asList("AIS"));
        gg.setClientAuthenticationScheme(AuthenticationScheme.form);
        gg.setAuthenticationScheme(AuthenticationScheme.header);
        //gg.setPreEstablishedRedirectUri("/main");
        //gg.setId("github2");

        return gg;
    }

    @Autowired
    public void oauth2ResourceServerProperties(ResourceServerProperties props) {
        props.setUserInfoUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/account-information/my/accounts");
        //props.setUserInfoUri("https://webapi.developers.erstegroup.com/api/csas/sandbox/v1/account-information");
    }

}
