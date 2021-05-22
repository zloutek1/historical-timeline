package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequestMapping("/event")
public class EventController {
    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    @Inject
    private EventFacade eventFacade;

    @GetMapping(value = "/new")
    public String getCreate(Model model){
        LOG.debug("get new Event");
        model.addAttribute("event", new EventCreateDTO());
        return "event/form";
    }

    @PostMapping(value = "/new")
    public String postCreate(Model model, @Valid @ModelAttribute("event") EventCreateDTO event, @RequestParam(required = false) Long timelineId){
        LOG.debug("post new Event");

        if (isDuplicate(model, event.getName())){
            return "event/form";
        }

        eventFacade.createEvent(event);

        return redirect(timelineId);
    }

    @GetMapping(value = "/update/{id}")
    public String getUpdate(Model model, @PathVariable Long id){
        LOG.debug("get Event");

        var event = eventFacade.findById(id);
        if (event.isEmpty()){
            LOG.debug("Event with id {} does not exist", id);
            model.addAttribute("event_not_found", "Event does not exist");
            return "redirect:/home";
        }

        model.addAttribute("event", event.get());
        return "event/form";
    }

    @PutMapping(value = "/update")
    public String putUpdate(Model model, @Valid @ModelAttribute("event") EventDTO event, @RequestParam(required = false) Long timelineId){
        LOG.debug("post Event");

        if (isDuplicate(model, event.getName())){
            return "event/form";
        }

        eventFacade.updateEvent(event);

        return redirect(timelineId);
    }

    private Boolean isDuplicate(Model model, String eventName) {
        var duplicate = eventFacade.findByName(eventName);
        if (duplicate.isPresent()){
            LOG.debug("Event with name {} already exists", eventName);
            model.addAttribute("duplicate_event", "Event already exists");
            return true;
        }
        return false;
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(Model model, @PathVariable Long id, @RequestParam(required = false) Long timelineId){
        LOG.debug("delete Event");

        var event  = eventFacade.findById(id);
        if (event.isEmpty()){
            LOG.debug("Event with id {} does not exist", id);
            model.addAttribute("event_not_found", "Event does not exist");
        } else if (!eventFacade.findTimelines(id).isEmpty()){
            LOG.debug("Event with id {} is used in other Timelines", id);
            model.addAttribute("event_used", "Event is used in other Timelines");
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
}
