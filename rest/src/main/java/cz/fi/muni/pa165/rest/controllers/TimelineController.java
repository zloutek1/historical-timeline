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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timelines")
public class TimelineController {
    @Inject
    private TimelineFacade timelineFacade;

    /**
     * Create a new timeline by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"name":"test","fromDate":"1000-02-01","toDate":"1000-02-02", "studyGroup":"UNDEFINED"}'
     * http://localhost:8080/pa165/rest/timeline/create
     *
     * @param timeline TimelineCreateDTO with required fields for creation
     * @return the created timeline TimelineDTO
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO createTimeline(@RequestBody TimelineCreateDTO timeline){
        try {
            Long id = timelineFacade.createTimeline(timeline);
            return timelineFacade.findById(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ServiceException e){
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Update a timeline by PUT method
     * curl -X PUT -i -H "Content-Type: application/json" --data
     * '{"name":"test","fromDate":"1000-02-01","toDate":"1000-02-02"}'
     * http://localhost:8080/pa165/rest/timeline/update/1
     *
     * @param timeline TimelineUpdateDTO with required fields for creation
     * @return the updated timeline TimelineDTO
     */
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO updateTimeline(@RequestBody TimelineUpdateDTO timeline){
        try {
            timelineFacade.updateTimeline(timeline);
            return timelineFacade.findById(timeline.getId()).orElseThrow(ResourceNotFoundException::new);
        } catch (ServiceException e){
            throw new ResourceNotModifiedException();
        }
    }

    /**
     * Delete a timeline by DELETE method
     * curl -X DELETE
     * http://localhost:8080/pa165/rest/timeline/delete/1
     *
     * @param id of the soon to be deleted Timeline
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTimeline(@PathVariable Long id){
        try {
            timelineFacade.deleteTimeline(id);
        } catch (ServiceException e){
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get list of all Timelines
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/timelines
     *
     * @return List of all TimelineDTOs
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAll(){
        return timelineFacade.findAll();
    }

    /**
     * Get list of Timelines in given time frame
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/timelines/between/1000-02-01/1000-02-02
     *
     * @return List of TimelineDTOs in given time frame
     */
    @GetMapping(value = "/between/{since}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TimelineDTO> findAllBetweenDates(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate since, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return timelineFacade.findAllBetweenDates(since, to);
    }

    /**
     * Get Timeline by id
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/timeline/1
     *
     * @return TimelineDTO with given id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findById(@PathVariable Long id){
        return timelineFacade.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Get Timeline by name
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/timeline/test
     *
     * @return TimelineDTO with given name
     */
    @GetMapping(value = "/named/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final TimelineDTO findByName(@PathVariable("name") String name){
        return timelineFacade.findByName(name).orElseThrow(ResourceNotFoundException::new);
    }
}
