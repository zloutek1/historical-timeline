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
    void create(Event event);

    void addTimeline(Event event, Timeline timeline);
    void removeTimeline(Event event, Timeline timeline);

    Optional<Event> getById(Long eventId);
    Optional<Event> getByName(String name);
    List<Event> getAllEvents();
    List<Event> getAllInRange(LocalDate since, LocalDate to);
    List<Event> getByLocation(String location);
    List<Event> getByDescription(String description);
}
