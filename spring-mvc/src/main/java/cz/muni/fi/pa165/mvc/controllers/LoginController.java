package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Ondřej Machala
 */
@Controller
@RequestMapping("/auth")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private UserFacade userFacade;

    @GetMapping(value = "login")
    public String getLogin(Model model, HttpSession session) {
        LOG.debug("get login");
        if (session.getAttribute("authUser") != null) {
            LOG.debug("get login - already logged in -> redirecting");
            return "redirect:/home";
        }
        model.addAttribute("user", new UserAuthenticateDTO());
        return "/auth/login";
    }

    @PostMapping(value = "login")
    public String postLogin(Model model, HttpSession session,
                            @Valid @ModelAttribute("user") UserAuthenticateDTO user,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post login");
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        Optional<UserDTO> optUser = userFacade.findUserByEmail(user.getEmail());
        if (optUser.isEmpty()) {
            LOG.debug("post login - user not in database");
            redirectAttributes.addFlashAttribute("login_failure", "User does not exist");
            return "redirect:/auth/login";
        }
        UserDTO userDTO = optUser.get();
        if (!userFacade.authenticate(user)) {
            LOG.debug("post login - invalid password");
            redirectAttributes.addFlashAttribute("login_failure", "Invalid password");
            return "redirect:/auth/login";
        }
        session.setAttribute("authUser", userDTO);
        LOG.debug("post login - Successfully logged in user {}", userDTO);
        return "redirect:/home";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("authUser") == null)
            return "redirect:/";
        session.removeAttribute("authUser");
        return "redirect:/auth/login";
    }
}
