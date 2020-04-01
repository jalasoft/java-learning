package cz.jalasoft.erstebank.pds2.client.endpoint.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author lastovicka
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String main(Principal p, Model model) {
        model.addAttribute("name", "hardcoded_user");
        return "/main.html";
    }
}
