package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Eva Krajníková
 */

@Controller
@RequestMapping("/event")
public class EventController implements HandlerExceptionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    @Inject
    private EventFacade eventFacade;

    @Inject
    private TimelineFacade timelineFacade;

    @GetMapping(value = "/new")
    public String getCreate(Model model){
        LOG.debug("get new Event");
        model.addAttribute("event", new EventCreateDTO());
        return "event/form";
    }

    @PostMapping(value = "/new")
    public String postCreate(Model model,
                             @Valid @ModelAttribute("event") EventCreateDTO event,
                             BindingResult bindingResult,
                             @RequestParam(required = false) Long timelineId){
        LOG.debug("post new Event");

        if (bindingResult.hasErrors() || isDuplicate(model, event.getName(), null)){
            return "event/form";
        }

        Long id = eventFacade.createEvent(event);
        try {
            eventFacade.addTimeline(id, timelineId);
        } catch (ServiceException e){
            model.addAttribute("alert_danger", e.getMessage());
            return "event/form";
        }

        return redirect(timelineId);
    }

    @GetMapping(value = "/update/{id}")
    public String getUpdate(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        LOG.debug("get Event");

        var event = eventFacade.findById(id);
        if (event.isEmpty()){
            LOG.debug("Event with id {} does not exist", id);
            redirectAttributes.addFlashAttribute("alert_danger", "Event does not exist");
            return "redirect:/home";
        }

        model.addAttribute("event", event.get());
        return "event/form";
    }

    @PostMapping(value = "/update/{id}")
    public String postUpdate(Model model,
                             @Valid @ModelAttribute("event") EventDTO event,
                             BindingResult bindingResult,
                             @RequestParam(required = false) Long timelineId){
        LOG.debug("post Event");

        if (bindingResult.hasErrors() || isDuplicate(model, event.getName(), event.getId())){
            return "event/form";
        }

        eventFacade.updateEvent(event);

        timelineCheck(event, timelineId);

        return redirect(timelineId);
    }

    private Boolean isDuplicate(Model model, String eventName, Long id) {
        var duplicate = eventFacade.findByName(eventName);
        if (duplicate.isPresent() && !duplicate.get().getId().equals(id)){
            LOG.debug("Event with name {} already exists", eventName);
            model.addAttribute("duplicate_event", "Event already exists");
            return true;
        }
        return false;
    }

    private void timelineCheck(EventDTO event, Long timelineId){
        if (timelineId == null){
            return;
        }

        var timeline = timelineFacade.findById(timelineId);

        if (timeline.isPresent() && !outOfBounds(event, timeline.get())){
            eventFacade.removeTimeline(event.getId(), timelineId);
        }
    }

    private Boolean outOfBounds(EventDTO event, TimelineDTO timeline){
        return event.getDate().isAfter(timeline.getFromDate()) && event.getDate().isBefore(timeline.getToDate());
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(Model model,
                         @PathVariable Long id,
                         @RequestParam(required = false) Long timelineId,
                         RedirectAttributes redirectAttributes){
        LOG.debug("delete Event");

        var event  = eventFacade.findById(id);
        if (event.isEmpty()){
            LOG.debug("Event with id {} does not exist", id);
            redirectAttributes.addFlashAttribute("alert_danger", "Event does not exist");
        } else if (!eventFacade.findTimelines(id).isEmpty()){
            LOG.debug("Event with id {} is used in other Timelines", id);
            redirectAttributes.addFlashAttribute("alert_danger", "Event is used in other Timelines");
        } else {
            eventFacade.deleteEvent(id);
        }

        return redirect(timelineId);
    }

    private String redirect(Long timelineId){
        if (timelineId == null){
            return "redirect:/home";
        }
        return "redirect:/timeline/" + timelineId;
    }

    @GetMapping("/{id}/image")
    public void getImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        Optional<EventDTO> optEventDTO = eventFacade.findById(id);
        if (optEventDTO.isEmpty()) {
            return;
        }

        EventDTO eventDTO = optEventDTO.get();

        byte[] image = eventDTO.getImage();
        if(image != null) {
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView("home");
        var model = modelAndView.getModel();
        if (e instanceof MaxUploadSizeExceededException) {
            model.put("alert_danger", e.getMessage());
        }
        return modelAndView;
    }
}
