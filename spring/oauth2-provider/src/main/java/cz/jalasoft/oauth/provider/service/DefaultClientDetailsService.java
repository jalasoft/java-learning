package cz.jalasoft.oauth.provider.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lastovicka
 */
@Primary
@Component
public class DefaultClientDetailsService implements ClientDetailsService {

    @PostConstruct
    public void f() {
        System.out.println();
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        if (!"client1".equals(clientId)) {
            throw new ClientRegistrationException("Nenasel jsem klienta vole.");
        }

        BaseClientDetails d = new BaseClientDetails();
        d.setClientId("client1");
        d.setClientSecret("secret1");

        Set<String> redirectUris = new HashSet<>();
        redirectUris.add("http://localhost:8888/login/oauth2/code/local");
        d.setRegisteredRedirectUri(redirectUris);

        d.setScope(Arrays.asList("read", "write", "ostatni"));
        d.setAuthorizedGrantTypes(Arrays.asList( "authorization_code", "refresh_token"));
        d.setAccessTokenValiditySeconds(5);
        d.setRefreshTokenValiditySeconds(100_000);

        return d;
    }
}
