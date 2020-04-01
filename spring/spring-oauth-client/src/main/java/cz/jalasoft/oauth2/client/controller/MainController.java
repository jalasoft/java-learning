package cz.jalasoft.oauth2.client.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author lastovicka
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String redirection() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        OAuth2Authentication authentication = (OAuth2Authentication) context.getAuthentication();

        UsernamePasswordAuthenticationToken tokenAuth = (UsernamePasswordAuthenticationToken) authentication.getUserAuthentication();
        Map<String, String> details = (Map<String, String>) tokenAuth.getDetails();

        String name = details.get("name");
        model.addAttribute("name", name);

        return "main.html";
    }
}
