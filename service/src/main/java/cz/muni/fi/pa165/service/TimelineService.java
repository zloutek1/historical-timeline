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
    void create(Timeline timeline);
    void addEvent(Timeline timeline, Event event);
    void removeEvent(Timeline timeline, Event event);
    void addComment(Timeline timeline, Comment comment);
    void removeComment(Timeline timeline, Comment comment);
    void delete(Timeline timeline);
    List<Timeline> getAll();
    List<Timeline> getAllBetweenDates(LocalDate from, LocalDate to);
    Optional<Timeline> getById(Long id);
    Optional<Timeline> getByName(String name);
}
