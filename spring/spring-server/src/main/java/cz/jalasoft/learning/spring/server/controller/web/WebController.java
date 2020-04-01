package cz.jalasoft.learning.spring.server.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping("/")
    public String rootRedirecting() {
        return "redirect:/web/index";
    }


    @GetMapping("/web/index")
    public String mainPage(
            Model model,
            @RequestParam(name = "name", defaultValue = "world") String name) {

        model.addAttribute("name", name);

        return "index";
    }
}
