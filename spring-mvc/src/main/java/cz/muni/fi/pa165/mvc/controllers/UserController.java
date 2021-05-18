package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.facade.UserFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserFacade userFacade;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userFacade.findAllUsers());
        return "user/list";
    }

}
