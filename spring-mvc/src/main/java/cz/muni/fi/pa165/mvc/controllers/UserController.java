package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private UserFacade userFacade;

    @GetMapping
    public String list(Model model) {
        LOG.debug("user list");
        model.addAttribute("users", userFacade.findAllUsers());
        return "user/list";
    }

    @GetMapping(value = "new")
    public String getNew(Model model) {
        LOG.debug("get user new");
        model.addAttribute("user", new UserCreateDTO());
        return "user/new";
    }

    @PostMapping(value = "new")
    public String postNew(Model model, HttpSession session,
                          @Valid @ModelAttribute("user") UserCreateDTO user,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post user new");
        if (bindingResult.hasErrors()) {
            return "user/new";
        }
        Optional<UserDTO> optUser = userFacade.findUserByEmail(user.getEmail());
        if (optUser.isPresent()) {
            LOG.debug("post user new - user with email {} already in database", user.getEmail());
            model.addAttribute("new_user_failure", "User already exists");
            return "user/new";
        }
        userFacade.registerUser(user, "password");
        LOG.debug("post user new - Successfully added new user {}", user);
        return "redirect:/user";
    }

    @PostMapping(value = "delete/{id}")
    public String delete(@PathVariable long id, Model model,
                         HttpSession session, RedirectAttributes redirectAttributes) {
        LOG.debug("user delete {}", id);
        var optUser = userFacade.findUserByID(id);
        if (optUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_danger", "No user with id " + id);
            return "redirect:/user";
        }
        UserDTO user = optUser.get();
        UserDTO authUser = (UserDTO)session.getAttribute("authUser");
        if (user.getId() == authUser.getId()) {
            LOG.debug("user delete - user with " + user.getId() + " attempted to delete himself");
            redirectAttributes.addFlashAttribute("alert_danger", "Can't delete yourself");
        } else  {
            userFacade.deleteUser(user.getId());
            LOG.debug("user delete - user with " + user.getId() + " attempted to delete himself");
            redirectAttributes.addFlashAttribute("alert_success",
                    "Successfully deleted user " + user.getEmail());
        }
        return "redirect:/user";
    }

}
