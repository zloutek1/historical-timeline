package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.facade.StudyGroupFacade;
import cz.muni.fi.pa165.facade.TimelineFacade;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author David Sevcik
 */
@Controller
@RequestMapping("/studygroup")
public class StudyGroupController {
    private static final Logger LOG = LoggerFactory.getLogger(StudyGroupController.class);

    @Inject
    private StudyGroupFacade studyGroupFacade;

    @Inject
    private UserFacade userFacade;

    @Inject
    private TimelineFacade timelineFacade;

    @Inject
    private EventFacade eventFacade;

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

        var authUser = (UserDTO)session.getAttribute("authUser");

        studyGroup.setLeader(authUser.getId());

        var studyGroupID = studyGroupFacade.createStudyGroup(studyGroup);
        userFacade.registerToStudyGroup(authUser.getId(), studyGroupID);

        LOG.debug("post studygroup new - Successfully added new studygroup {}", studyGroup);
        redirectAttributes.addFlashAttribute("alert_success",
                "Added Study group " + studyGroup.getName());
        return "redirect:/home";
    }

    @PostMapping(value = "delete/{id}")
    public String postDelete(@PathVariable long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO authUser = (UserDTO)session.getAttribute("authUser");
        if (authUser != null) {
            if ((authUser.getRole() == UserRole.TEACHER) || (authUser.getRole() == UserRole.ADMINISTRATOR)) {

                var studyGroup = studyGroupFacade.findById(id);
                if (studyGroup.isEmpty()) {
                    redirectAttributes.addFlashAttribute("alert_danger", "Failed to remove study group.");
                    return "redirect:/home";
                }

                var timelines = studyGroup.get().getTimelines();
                for (var timeline: timelines
                     ) {
                    var events = timelineFacade.findById(timeline.getId()).get().getEvents();
                    for (var event: events) {
                        timelineFacade.removeEvent(timeline.getId(), event.getId());
                        eventFacade.removeTimeline(event.getId(), timeline.getId());
                    }
                }

                var members = studyGroup.get().getMembers();
                for (var member: members
                     ) {
                    userFacade.unregisterFromStudyGroup(member.getId(), id);
                }

                studyGroupFacade.deleteStudyGroup(id);
            }
        }
        return "redirect:/home";
    }

    @PostMapping(value = "unregister/{studygroup_id}/{user_id}")
    public String postUnregister(@PathVariable long studygroup_id, @PathVariable long user_id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        var studygroup = studyGroupFacade.findById(studygroup_id);
        if (studygroup.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_danger", "Failed to remove user from study group.");
            return "redirect:/home";
        }
        if (studygroup.get().getLeader().getId() == user_id) {
            redirectAttributes.addFlashAttribute("alert_danger", "Failed to remove user from study group, since the user is leader of the study group.");
            return "redirect:/home";
        }
        userFacade.unregisterFromStudyGroup(user_id, studygroup_id);
        return "redirect:/home";
    }

    @PostMapping(value = "addmember")
    public String addMember(@ModelAttribute("email") String email, @RequestParam("studyGroupId") long studyGroupId,
                            RedirectAttributes redirectAttributes) {
        LOG.debug("add member - " + studyGroupId + " - " + email);
        var optUser = userFacade.findUserByEmail(email);
        if (optUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_danger", "Could not find user with email " + email);
            return "redirect:/home";
        }
        var optStudyGroup = studyGroupFacade.findById(studyGroupId);
        if (optStudyGroup.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_danger", "Could not study group with id " + studyGroupId);
            return "redirect:/home";
        }
        var user = optUser.get();
        var studyGroup = optStudyGroup.get();
        var userStudyGroups = userFacade.findUserStudyGroups(user.getId());
        if (userStudyGroups.contains(studyGroup)) {
            redirectAttributes.addFlashAttribute("alert_danger", "User already in study group");
            return "redirect:/home";
        }
        userFacade.registerToStudyGroup(user.getId(), studyGroupId);
        redirectAttributes.addFlashAttribute("alert_success", "Successfully added user to study group");
        return "redirect:/home";
    }

}
