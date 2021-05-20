package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.facade.StudyGroupFacade;
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

@Controller
@RequestMapping("/studygroup")
public class StudyGroupController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private StudyGroupFacade studyGroupFacade;

    @GetMapping
    public String list(Model model) {
        LOG.debug("studygroup list");
        model.addAttribute("studygroups", studyGroupFacade.findAllStudyGroups());
        return "studygroup/list";
    }

    @GetMapping(value = "new")
    public String getNew(Model model) {
        LOG.debug("get studygroup new");
        model.addAttribute("studyGroup", new StudyGroupCreateDTO());
        return "studygroup/new";
    }

    @PostMapping(value = "new")
    public String postNew(Model model, HttpSession session,
                          @Valid @ModelAttribute("studyGroup") StudyGroupCreateDTO studyGroup,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post studygroup new");
        if (bindingResult.hasErrors()) {
            return "studygroup/new";
        }
        Optional<StudyGroupDTO> optStudyGroup = studyGroupFacade.findByName(studyGroup.getName());

        if (optStudyGroup.isPresent()) {
            LOG.debug("post studygroup new - studygroup with name {} already in database", studyGroup.getName());
            model.addAttribute("new_studygroup_failure", "Study group already exists");
            return "studygroup/new";
        }
        studyGroupFacade.createStudyGroup(studyGroup);
        LOG.debug("post studygroup new - Successfully added new studygroup {}", studyGroup);
        redirectAttributes.addFlashAttribute("alert_success",
                "Added user " + studyGroup.getName());
        return "redirect:/studygroup";
    }

}
