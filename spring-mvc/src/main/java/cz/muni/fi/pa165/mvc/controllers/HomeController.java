package cz.muni.fi.pa165.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

}
