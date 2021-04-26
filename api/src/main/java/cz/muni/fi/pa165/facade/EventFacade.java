package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Eva Krajíková
 */

public interface EventFacade {
    void createEvent(EventCreateDTO event);

    void setName(Long eventId, EventCreateDTO name);
    void setDate(Long eventId, LocalDate date);
    void setLocation(Long eventId, String location);
    void setDescription(Long eventId, String description);
    void setImageIdentifier(Long eventId, String imageIdentifier);
    void addTimeline(Long eventId, Long timelineId);
    void removeTimeline(Long eventId, Long timelineId);

    Optional<EventDTO> getById(Long eventId);
    Optional<EventDTO> getByName(String name);
    List<EventDTO> getAllEvents();
    List<EventDTO> getAllInRange(LocalDate since, LocalDate to);
    List<EventDTO> getByLocation(String location);
    List<EventDTO> getByDescription(String description);
    List<TimelineDTO> getTimelines(Long eventId);
}

