package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.TimelineService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
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

        setImage(mappedEvent, event.getImage());

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

        setImage(event, updatedEvent.getImage());
    }

    private void setImage(Event event, MultipartFile image){
        if (image == null) {
            event.setImage(null);
            return;
        }

        try {
            event.setImage(image.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public void deleteEvent(Long eventId){
        eventService.delete(getEvent(eventId));
    }

    @Override
    public void addTimeline(Long eventId, Long timelineId) {
        getEvent(eventId).addTimeline(getTimeline(timelineId));
    }

    @Override
    public void removeTimeline(Long eventId, Long timelineId) {
        getEvent(eventId).removeTimeline(getTimeline(timelineId));
    }

    private Event getEvent(Long eventId){
        Optional<Event> event = eventService.findById(eventId);

        if (event.isEmpty()){
            throw new IllegalArgumentException("Could not find Event by ID: " + eventId);
        }

        return event.get();
    }

    private Timeline getTimeline(Long timelineId){
        Optional<Timeline> timeline = timelineService.findById(timelineId);

        if (timeline.isEmpty()){
            throw new IllegalArgumentException("Could not find Timeline by ID: " + timelineId);
        }

        return timeline.get();
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
