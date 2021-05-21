package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CommentDTO;
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
import java.util.Optional;

@Controller
@RequestMapping("/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Inject
    private TimelineFacade timelineFacade;

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model,
                       RedirectAttributes redirectAttributes) {
        LOG.debug("show timeline");
        Optional<TimelineDTO> timelineOpt = timelineFacade.findById(id);
        if( timelineOpt.isEmpty() ) {
            redirectAttributes.addFlashAttribute("alert_danger", "No timeline with id " + id);
            return "redirect:/home";
        }

        model.addAttribute("timeline", timelineOpt.get());
        model.addAttribute("comment", new CommentDTO());
        return "timeline/view";
    }
}
