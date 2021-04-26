package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Event;
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
        eventService.createEvent(mappedEvent);
    }

    @Override
    public void setName(Long eventId, EventCreateDTO name) {
        Optional<Event> event = eventService.getById(eventId);
        event.orElseThrow(IllegalArgumentException::new)
                .setName(name.getName());
    }

    @Override
    public void setDate(Long eventId, LocalDate date) {
        Optional<Event> event = eventService.getById(eventId);
        event.orElseThrow(IllegalArgumentException::new)
                .setDate(date);

    }

    @Override
    public void setLocation(Long eventId, String location) {
        Optional<Event> event = eventService.getById(eventId);
        event.orElseThrow(IllegalArgumentException::new)
                .setLocation(location);
    }

    @Override
    public void setDescription(Long eventId, String description) {
        Optional<Event> event = eventService.getById(eventId);
        event.orElseThrow(IllegalArgumentException::new)
                .setDescription(description);
    }

    @Override
    public void setImageIdentifier(Long eventId, String imageIdentifier) {
        Optional<Event> event = eventService.getById(eventId);
        event.orElseThrow(IllegalArgumentException::new)
                .setImageIdentifier(imageIdentifier);
    }

    @Override
    public void addTimeline(Long eventId, Long timelineId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeTimeline(Long eventId, Long timelineId) {
        throw new UnsupportedOperationException();
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
