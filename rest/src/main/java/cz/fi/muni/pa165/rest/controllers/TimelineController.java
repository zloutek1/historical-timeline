package cz.fi.muni.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timelines")
public class TimelineController {
    @Inject
    private TimelineFacade timelineFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO createTimeline(@RequestBody TimelineCreateDTO timeline){
        Long id = timelineFacade.createTimeline(timeline);
        return timelineFacade.findById(id).orElse(null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO updateTimeline(@RequestBody TimelineUpdateDTO timeline){
        timelineFacade.updateTimeline(timeline);
        return timelineFacade.findById(timeline.getId()).orElse(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTimeline(@PathVariable Long id){
        timelineFacade.deleteTimeline(id);
    }

    @RequestMapping(value = "/{timelineId}/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO addEvent(@PathVariable Long timelineId, @RequestBody EventDTO event){
        timelineFacade.addEvent(timelineId, event.getId());
        return timelineFacade.findById(timelineId).orElse(null);
    }

    @RequestMapping(value = "/{timelineId}/event/{eventId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO removeEvent(@PathVariable Long timelineId, @PathVariable Long eventId){
        timelineFacade.removeEvent(timelineId, eventId);
        return timelineFacade.findById(timelineId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAll(){
        return timelineFacade.findAll();
    }

    @RequestMapping(value = "/between/{since}/{to}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAllBetweenDates(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate since, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return timelineFacade.findAllBetweenDates(since, to);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findById(@PathVariable Long id){
        return timelineFacade.findById(id).orElse(null);
    }

    @RequestMapping(value = "/named/{name}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findByName(@PathVariable("name") String name){
        return timelineFacade.findByName(name).orElse(null);
    }


}
