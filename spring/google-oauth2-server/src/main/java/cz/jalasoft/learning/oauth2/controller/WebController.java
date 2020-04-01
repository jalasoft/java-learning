package cz.jalasoft.learning.oauth2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class WebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);


    @GetMapping("/")
    public String rootRedirection() {
        return "redirect:/welcome";
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/main")
    public String index(Model model) {

        LOGGER.debug("Request na main.html prisel.");

        SecurityContext context = SecurityContextHolder.getContext();
        OAuth2Authentication auth = (OAuth2Authentication) context.getAuthentication();

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String token = details.getTokenValue();

        Map<String, Object> map = (Map<String, Object>) auth.getUserAuthentication().getDetails();

        model.addAttribute("name", map.get("name"));
        model.addAttribute("pictureUrl", map.get("picture"));

        LOGGER.debug("Model naplnen.");
        LOGGER.debug("nasrat a nepustit k vode");

        return "main";
    }

}
