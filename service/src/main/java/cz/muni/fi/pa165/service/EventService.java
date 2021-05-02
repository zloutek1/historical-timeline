package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Eva Krajíková
 */

public interface EventService {
    /**
     * Stores an Event entity
     * @param event to be stored
     */
    void create(Event event);
    /**
     * Deletes an Event entity
     * @param event to be deleted
     */
    void delete(Event event);

    /**
     * Adds Timeline to Event
     * @param event Event to be updated
     * @param timeline Timeline to be added
     */
    void addTimeline(Event event, Timeline timeline);

    /**
     * Removes Timeline from Event
     * @param event Event to be updated
     * @param timeline Timeline to be removed
     */
    void removeTimeline(Event event, Timeline timeline);

    /**
     * Fetches Event by id
     * @param eventId id of the Event
     * @return Event if found, else empty
     */
    Optional<Event> findById(Long eventId);

    /**
     * Fetches Event by name
     * @param name of the Event
     * @return Event if found, else empty
     */
    Optional<Event> findByName(String name);

    /**
     * Fetches Events by date range
     * @param since  starting date
     * @param to ending date
     * @return Event if found, else empty
     */
    List<Event> findAllInRange(LocalDate since, LocalDate to);

    /**
     * Fetches Event by location
     * @param location of the Event
     * @return Event if found, else empty
     */
    List<Event> findByLocation(String location);

    /**
     * Fetches Event by description
     * @param description of the Event
     * @return Event if found, else empty
     */
    List<Event> findByDescription(String description);

    /**
     * Fetches all Events
     * @return List of all Events
     */
    List<Event> findAllEvents();
}
