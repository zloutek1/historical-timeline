package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineAddEventDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.facade.TimelineFacade;
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
import java.util.Comparator;
import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
@Controller
@RequestMapping("/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Inject
    private TimelineFacade timelineFacade;

    @Inject
    private EventFacade eventFacade;

    @GetMapping("/{id}")
    public String show(Model model, HttpSession session,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes) {
        LOG.debug("show timeline");
        Optional<TimelineDTO> timelineOpt = timelineFacade.findById(id);
        if( timelineOpt.isEmpty() ) {
            redirectAttributes.addFlashAttribute("alert_danger", "No timeline with id " + id);
            return "redirect:/home";
        }

        timelineOpt.get().getEvents().sort(Comparator.comparing(EventDTO::getDate));
        model.addAttribute("timeline", timelineOpt.get());

        var authUser = (UserDTO) session.getAttribute("authUser");
        model.addAttribute("authUser", authUser);

        var comment = new CommentCreateDTO();
        comment.setTimelineId(id);
        model.addAttribute("comment", comment);

        var addEvent = new TimelineAddEventDTO();
        addEvent.setTimelineId(id);
        model.addAttribute("addEvent", addEvent);

        return "timeline/view";
    }

    @GetMapping("/new")
    public String getNew(Model model, @RequestParam Long studyGroupId) {
        LOG.debug("get timeline new");
        model.addAttribute("timeline", new TimelineCreateDTO());
        model.addAttribute("studyGroupId", studyGroupId);
        return "/timeline/new";
    }

    @PostMapping ("/new")
    public String postNew(Model model, HttpSession session,
                          @RequestParam Long studyGroupId,
                          @Valid @ModelAttribute("timeline") TimelineCreateDTO timeline,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post timeline new");
        if (bindingResult.hasErrors()) {
            model.addAttribute("studyGroupId", studyGroupId);
            return "/timeline/new";
        }

        if (timeline.getFromDate().isAfter(timeline.getToDate())) {
            model.addAttribute("new_timeline_failure", "Timeline before date must be after from date");
            return "/timeline/new";
        }

        Optional<TimelineDTO> optTimeline = timelineFacade.findByName(timeline.getName());
        if (optTimeline.isPresent()) {
            LOG.debug("post timeline new - timeline with name {} already in database", timeline.getName());
            model.addAttribute("new_timeline_failure", "Timeline already exists");
            return "/timeline/new";
        }
        timeline.setStudyGroupId(studyGroupId);
        timelineFacade.createTimeline(timeline);
        LOG.debug("post user new - Successfully added new timeline {}", timeline);
        redirectAttributes.addFlashAttribute("alert_success", "Added timeline " + timeline.getName());
        return "redirect:/home";
    }

    @GetMapping("/update/{id}")
    public String getUpdate(Model model, @PathVariable Long id) {
        LOG.debug("get timeline update");

        Optional<TimelineDTO> optTimeline = timelineFacade.findById(id);
        if(optTimeline.isEmpty()) {
            LOG.debug("get timeline update - timeline with id {} not found", id);
            model.addAttribute("update_timeline_failure", "Timeline not found");
            return "/timeline/update";
        }

        var timeline = optTimeline.get();
        var timelineUpdate = new TimelineUpdateDTO();
        timelineUpdate.setId(timeline.getId());
        timelineUpdate.setName(timeline.getName());
        timelineUpdate.setFromDate(timeline.getFromDate());
        timelineUpdate.setToDate(timeline.getToDate());

        model.addAttribute("timeline", timelineUpdate);

        return "/timeline/update";
    }

    @PostMapping ("/update")
    public String postNew(Model model,
                          @Valid @ModelAttribute TimelineUpdateDTO timeline,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post timeline update");
        if (bindingResult.hasErrors()) {
            return "/timeline/update";
        }
        timelineFacade.updateTimeline(timeline);
        LOG.debug("post timeline update - Successfully updated timeline with id {} to {}", timeline.getId(), timeline);
        redirectAttributes.addFlashAttribute("alert_success", "Updated timeline " + timeline.getName());
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        LOG.debug("post timeline delete");
        var timeline = timelineFacade.findById(id);
        if (timeline.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_danger", "Failed to remove timeline.");
            return "redirect:/home";
        }
        var events = timeline.get().getEvents();
        for (var event: events) {
            timelineFacade.removeEvent(id, event.getId());
        }

        timelineFacade.deleteTimeline(id);
        LOG.debug("post timeline delete - Successfully deleted timeline with id {}", id);
        return "redirect:/home";
    }

    @GetMapping("/{id}/add/event")
    public String getAddEvent(Model model, @PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        LOG.debug("get timeline add all events");

        Optional<TimelineDTO> timelineOpt = timelineFacade.findById(id);
        if( timelineOpt.isEmpty() ) {
            redirectAttributes.addFlashAttribute("alert_danger", "No timeline with id " + id);
            return "redirect:/home";
        }
        var timeline = timelineOpt.get();
        model.addAttribute("timeline", timeline);

        var addEventDTO = new TimelineAddEventDTO();
        addEventDTO.setTimelineId(id);
        model.addAttribute("addEvent", addEventDTO);

        var events = eventFacade.findAllInRange(timeline.getFromDate(), timeline.getToDate());
        events.removeAll(timeline.getEvents());
        model.addAttribute("events", events);

        if (events.size() == 0) {
            return "redirect:/event/new?timelineId=" + id;
        }

        return "/timeline/add/event";
    }

    @PostMapping("/add/event")
    public String postAddEvent(Model model,
                               @Valid @ModelAttribute("addEvent") TimelineAddEventDTO addEventDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOG.debug("post timeline add event");

        if (bindingResult.hasErrors()) {
            model.addAttribute("addEvent", addEventDTO);
            return "/timeline/add/event";
        }

        Long eventId = addEventDTO.getEventId();

        try {
            timelineFacade.addEvent(addEventDTO.getTimelineId(), eventId);
            redirectAttributes.addFlashAttribute("alert_success", "Added event " + eventId);
        } catch (SecurityException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Failed to add event " + eventId);
        }

        return "redirect:/timeline/" + addEventDTO.getTimelineId();
    }

    @PostMapping("/{timelineId}/remove/event/{eventId}")
    public String postRemoveEvent(Model model,
                                  @PathVariable Long timelineId, @PathVariable Long eventId,
                                  RedirectAttributes redirectAttributes) {
        LOG.debug("post timeline remove event");

        try {
            timelineFacade.removeEvent(timelineId, eventId);
            redirectAttributes.addFlashAttribute("alert_success", "Removed event " + eventId);
        } catch (SecurityException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Failed to remove event " + eventId);
        }

        return "redirect:/timeline/" + timelineId;
    }
}
