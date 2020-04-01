package cz.jalasoft.oauth.provider.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lastovicka
 */
@RestController
@RequestMapping("/api")
public class ResourceController {

    @PreAuthorize("CLIENT")
    @GetMapping("/user")
    public Map<String, Object> user(Principal p) {

        OAuth2Authentication auth = (OAuth2Authentication) p;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth.getUserAuthentication();
        User user = (User) token.getPrincipal();

        Map<String, Object> info = new HashMap<>();

        info.put("name", user.getUsername());
        String authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        info.put("authorities", authorities);

        return info;
    }

    @PreAuthorize("ACCOUNT_HOLDER")
    @GetMapping("/accounts")
    public List<String> accountNumbers(Principal p) {
        return Arrays.asList("133455/452", "44446544/125");
    }
}
