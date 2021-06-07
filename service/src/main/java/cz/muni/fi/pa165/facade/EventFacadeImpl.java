package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.TimelineService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Eva Krajíková
 */

@Service
@Transactional
public class EventFacadeImpl implements EventFacade{

    @Inject
    private EventService eventService;

    @Inject
    private TimelineService timelineService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createEvent(EventCreateDTO event) {
        Event mappedEvent = new Event();

        mappedEvent.setName(event.getName());
        mappedEvent.setDate(event.getDate());
        mappedEvent.setLocation(event.getLocation());
        mappedEvent.setDescription(event.getDescription());
        mappedEvent.setImage(event.getImage());

        eventService.create(mappedEvent);
        return mappedEvent.getId();
    }

    @Override
    public void updateEvent(EventDTO updatedEvent) {
        Event event = getEvent(updatedEvent.getId());

        event.setName(updatedEvent.getName());
        event.setDate(updatedEvent.getDate());
        event.setLocation(updatedEvent.getLocation());
        event.setDescription(updatedEvent.getDescription());
        event.setImage(updatedEvent.getImage());
    }

    @Override
    public void deleteEvent(Long eventId){
        eventService.delete(getEvent(eventId));
    }

    @Override
    public void addTimeline(Long eventId, Long timelineId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No Timeline with id " + timelineId + " found"));
        Event event = eventService.findById(eventId)
                .orElseThrow(() -> new ServiceException("No Event with id " + eventId + " found"));

        if (!inBounds(event.getDate(), timeline)) {
            throw new ServiceException("Event with id " + eventId + " out of bounds for Timeline with " + timelineId);
        }

        timeline.addEvent(event);
        event.addTimeline(timeline);
    }

    private Boolean inBounds(LocalDate eventDate, Timeline timeline){
        return (eventDate.isAfter(timeline.getFromDate()) &&
                eventDate.isBefore(timeline.getToDate())) ||
                eventDate.isEqual(timeline.getFromDate()) ||
                eventDate.isEqual(timeline.getToDate());
    }

    @Override
    public void removeTimeline(Long eventId, Long timelineId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineId + " found"));
        Event event = eventService.findById(eventId)
                .orElseThrow(() -> new ServiceException("No event with id " + eventId + " found"));

        timeline.removeEvent(event);
        event.removeTimeline(timeline);
    }

    private Event getEvent(Long eventId){
        Optional<Event> event = eventService.findById(eventId);

        if (event.isEmpty()){
            throw new IllegalArgumentException("Could not find Event by ID: " + eventId);
        }

        return event.get();
    }

    @Override
    public Optional<EventDTO> findById(Long eventId) {
        Optional<Event> event = eventService.findById(eventId);

        if (event.isEmpty()) {
            return Optional.empty();
        }

        EventDTO mappedEvent = beanMappingService.mapTo(event.get(), EventDTO.class);
        return Optional.of(mappedEvent);
    }

    @Override
    public Optional<EventDTO> findByName(String name) {
        Optional<Event> event = eventService.findByName(name);

        if (event.isEmpty()) {
            return Optional.empty();
        }

        EventDTO mappedEvent = beanMappingService.mapTo(event.get(), EventDTO.class);
        return Optional.of(mappedEvent);

    }

    @Override
    public List<EventDTO> findAllInRange(LocalDate since, LocalDate to) {
        return beanMappingService.mapTo(eventService.findAllInRange(since, to), EventDTO.class);
    }

    @Override
    public List<EventDTO> findByLocation(String location) {
        return beanMappingService.mapTo(eventService.findByLocation(location), EventDTO.class);
    }

    @Override
    public List<EventDTO> findByDescription(String description) {
        return beanMappingService.mapTo(eventService.findByDescription(description), EventDTO.class);
    }

    @Override
    public List<TimelineDTO> findTimelines(Long eventId) {
        Optional<Event> event = eventService.findById(eventId);

        if (event.isEmpty()){
            return new ArrayList<>();
        }

        return beanMappingService.mapTo(event.get().getTimelines(), TimelineDTO.class);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return beanMappingService.mapTo(eventService.findAllEvents(), EventDTO.class);
    }
}
