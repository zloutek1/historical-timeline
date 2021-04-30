package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Eva Krajíková
 */

@Service
public class EventFacadeImpl implements EventFacade{

    @Inject
    private EventService eventService;

    @Inject
    private TimelineService timelineService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void createEvent(EventCreateDTO event) {
        Event mappedEvent = beanMappingService.mapTo(event, Event.class);
        eventService.create(mappedEvent);
    }

    @Override
    public void setName(Long eventId, EventCreateDTO name) {
        getEvent(eventId).setName(name.getName());
    }

    @Override
    public void setDate(Long eventId, LocalDate date) {
        getEvent(eventId).setDate(date);
    }

    @Override
    public void setLocation(Long eventId, String location) {
        getEvent(eventId).setLocation(location);
    }

    @Override
    public void setDescription(Long eventId, String description) {
        getEvent(eventId).setDescription(description);
    }

    @Override
    public void setImageIdentifier(Long eventId, String imageIdentifier) {
        getEvent(eventId).setImageIdentifier(imageIdentifier);
    }

    @Override
    public void addTimeline(Long eventId, Long timelineId) {
        getEvent(eventId).addTimeline(getTimeline(timelineId));
    }

    @Override
    public void removeTimeline(Long eventId, Long timelineId) {
        throw new UnsupportedOperationException();
    }

    private Event getEvent(Long eventId){
        Optional<Event> event = eventService.getById(eventId);

        if (event.isEmpty()){
            throw new IllegalArgumentException("Could not find Event by ID: " + eventId);
        }

        return event.get();
    }

    private Timeline getTimeline(Long timelineId){
        Optional<Timeline> timeline = timelineService.getById(timelineId);

        if (timeline.isEmpty()){
            throw new IllegalArgumentException("Could not find Timeline by ID: " + timelineId);
        }

        return timeline.get();
    }

    @Override
    public Optional<EventDTO> getById(Long eventId) {
        Optional<Event> event = eventService.getById(eventId);

        if (event.isEmpty()) {
            return Optional.empty();
        }

        EventDTO mappedEvent = beanMappingService.mapTo(event.get(), EventDTO.class);
        return Optional.of(mappedEvent);
    }

    @Override
    public Optional<EventDTO> getByName(String name) {
        Optional<Event> event = eventService.getByName(name);

        if (event.isEmpty()) {
            return Optional.empty();
        }

        EventDTO mappedEvent = beanMappingService.mapTo(event.get(), EventDTO.class);
        return Optional.of(mappedEvent);

    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventService
                .getAllEvents()
                .stream()
                .map(event -> beanMappingService.mapTo(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllInRange(LocalDate since, LocalDate to) {
        return eventService
                .getAllInRange(since, to)
                .stream()
                .map(event -> beanMappingService.mapTo(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getByLocation(String location) {
        return eventService
                .getByLocation(location)
                .stream()
                .map(event -> beanMappingService.mapTo(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getByDescription(String description) {
        return eventService
                .getByDescription(description)
                .stream()
                .map(event -> beanMappingService.mapTo(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TimelineDTO> getTimelines(Long eventId) {
        Optional<Event> event = eventService.getById(eventId);

        if (event.isEmpty()){
            return new ArrayList<>();
        }

        return event
                .get()
                .getTimelines()
                .stream()
                .map(timeline -> beanMappingService.mapTo(timeline, TimelineDTO.class))
                .collect(Collectors.toList());
    }
}
