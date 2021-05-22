package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public String home(Model model, HttpSession session) {
        LOG.debug("home");

        UserDTO authUser = (UserDTO)session.getAttribute("authUser");

        List<StudyGroupDTO> userStudyGroups = new ArrayList<>();

        if (authUser != null) {
            userStudyGroups = (authUser.getRole() == UserRole.TEACHER)
                    ? userFacade.findLeadersStudyGroups(authUser.getId())
                    : userFacade.findUserStudyGroups(authUser.getId());
        }

        model.addAttribute("studyGroups", userStudyGroups);
        return "home";
    }

}
