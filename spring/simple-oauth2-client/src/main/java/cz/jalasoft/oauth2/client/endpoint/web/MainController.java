package cz.jalasoft.oauth2.client.endpoint.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collection;

/**
 * @author Jan Lastovicka
 * @since 2019-04-01
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String main(Principal principal, Model model) {

        OAuth2Authentication auth = (OAuth2Authentication) principal;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth.getUserAuthentication();

        String name = (String) token.getPrincipal();
        model.addAttribute("name", name);

        return "/main.html";
    }
}
