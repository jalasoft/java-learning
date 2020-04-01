package cz.jalasoft.oauth2.client.endpoint.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * @author lastovicka
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Secured("ROLE_ACCOUNT_HOLDER")
    @GetMapping("/accounts")
    public Collection<String> accounts(Principal p) {

        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/api/accounts", List.class);
        List<String> accounts = (List<String>) response.getBody();

        restTemplate.getAccessToken();

        return accounts;
    }
}
