package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotModifiedException;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Eva Krajníková
 */

@RestController
@RequestMapping("/timelines")
public class TimelineController {
    @Inject
    private TimelineFacade timelineFacade;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO createTimeline(@RequestBody TimelineCreateDTO timeline){
        try {
            Long id = timelineFacade.createTimeline(timeline);
            return timelineFacade.findById(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ServiceException e){
            throw new ResourceAlreadyExistingException();
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO updateTimeline(@RequestBody TimelineUpdateDTO timeline){
        try {
            timelineFacade.updateTimeline(timeline);
            return timelineFacade.findById(timeline.getId()).orElseThrow(ResourceNotFoundException::new);
        } catch (ServiceException e){
            throw new ResourceNotModifiedException();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTimeline(@PathVariable Long id){
        try {
            timelineFacade.deleteTimeline(id);
        } catch (ServiceException e){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAll(){
        return timelineFacade.findAll();
    }

    @GetMapping(value = "/between/{since}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAllBetweenDates(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate since, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return timelineFacade.findAllBetweenDates(since, to);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findById(@PathVariable Long id){
        return timelineFacade.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/named/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findByName(@PathVariable("name") String name){
        return timelineFacade.findByName(name).orElseThrow(ResourceNotFoundException::new);
    }

}
