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
    /**
     * Stores an Event
     * @param event information regarding events creation
     * @return id of new event
     */
    Long createEvent(EventCreateDTO event);

    /**
     * Updates an Event
     * @param updatedEvent information regarding events update
     */
    void updateEvent(EventDTO updatedEvent);

    /**
     * Deletes an Event
     * @param eventId id of the Event
     */
    void deleteEvent(Long eventId);

    /**
     * Adds Timeline to Event
     * @param eventId id of the Event
     * @param timelineId id of the Timeline
     */
    void addTimeline(Long eventId, Long timelineId);

    /**
     * Removes Timeline from Event
     * @param eventId id of the Event
     * @param timelineId id of the Timeline
     */
    void removeTimeline(Long eventId, Long timelineId);

    /**
     * Fetches Event by it's Id
     * @param eventId id of the Event
     * @return Event if found, else empty
     */
    Optional<EventDTO> findById(Long eventId);

    /**
     * Fetches Event by it's name
     * @param name name of the Event
     * @return Event if found, else empty
     */
    Optional<EventDTO> findByName(String name);

    /**
     * Fetches Events in date range
     * @param since starting date
     * @param to ending date
     * @return List of Events in given range
     */
    List<EventDTO> findAllInRange(LocalDate since, LocalDate to);

    /**
     * Fetches Events by it's location
     * @param location location of the Events
     * @return List of Events with given location
     */
    List<EventDTO> findByLocation(String location);

    /**
     * Fetches Events by it's location
     * @param description description of the Events
     * @return List of Events with given description
     */
    List<EventDTO> findByDescription(String description);

    /**
     * Fetches Timelines for given Event
     * @param eventId id of the Event
     * @return List of Timelines in Event
     */
    List<TimelineDTO> findTimelines(Long eventId);

    /**
     * Fetches all Events
     * @return List of all Events
     */
    List<EventDTO> findAllEvents();
}

