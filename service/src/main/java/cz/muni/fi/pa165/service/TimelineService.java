package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
public interface TimelineService {
    /**
     * Store a timeline entity
     * @param timeline to be stored
     */
    void create(Timeline timeline);

    /**
     * Updates a timeline entity
     * @param timeline to be updated
     */
    void update(Timeline timeline);

    /**
     * Deletes a timeline entity
     * @param timeline to be deleted
     */
    void delete(Timeline timeline);

    /**
     * Adds an event to a timeline
     * @param timeline to be updated
     * @param event to be added
     */
    void addEvent(Timeline timeline, Event event);

    /**
     * Removes an event from a timeline
     * @param timeline to be updated
     * @param event to be removed
     */
    void removeEvent(Timeline timeline, Event event);

    /**
     * Adds a comment to a timeline
     * @param timeline to be updated
     * @param comment to be added
     */
    void addComment(Timeline timeline, Comment comment);

    /**
     * Removes a comment from a timeline
     * @param timeline to be updated
     * @param comment to be removed
     */
    void removeComment(Timeline timeline, Comment comment);

    /**
     * @return all timelines
     */
    List<Timeline> findAll();

    /**
     * @param from starting date
     * @param to ending date
     * @return all timelines between dates
     */
    List<Timeline> findAllBetweenDates(LocalDate from, LocalDate to);

    /**
     * @param id of the timeline
     * @return timeline if the timeline is found else return empty
     */
    Optional<Timeline> findById(Long id);

    /**
     * @param name of the timeline
     * @return timeline if the timeline is found else return empty
     */
    Optional<Timeline> findByName(String name);
}
