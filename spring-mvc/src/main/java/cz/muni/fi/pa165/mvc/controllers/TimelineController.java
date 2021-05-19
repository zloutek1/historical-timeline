package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private TimelineFacade timelineFacade;

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model,
                       HttpSession session, RedirectAttributes redirectAttributes) {
        LOG.debug("show timeline");
        Optional<TimelineDTO> timelineOpt = timelineFacade.findById(id);
        if( timelineOpt.isEmpty() ) {
            redirectAttributes.addFlashAttribute("alert_danger", "No timeline with id " + id);
            return "redirect:/home";
        }
        LOG.debug("timeline: " + timelineOpt.get());
        LOG.debug("events: " + timelineOpt.get().getEvents());
        LOG.debug("comments: " + timelineOpt.get().getComments());
        model.addAttribute("timeline", timelineOpt.get());
        return "timeline/view";
    }

}
