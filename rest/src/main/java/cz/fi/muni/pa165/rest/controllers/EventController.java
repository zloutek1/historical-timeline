package cz.fi.muni.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Inject
    private EventFacade eventFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> findAllInRange(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate toDate){
        if (fromDate == null && toDate == null) {
            return eventFacade.findAllEvents();
        } else {
            return eventFacade.findAllInRange(fromDate, toDate);
        }
    }

}
