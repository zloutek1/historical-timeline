package cz.muni.fi.pa165.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String home(Model model) {
        log.debug("home");
        return "home";
    }

}
