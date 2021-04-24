package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;

import java.time.LocalDate;
import java.util.List;


public interface TimelineService {
    void createTimeline(Timeline timeline);
    void addEvent(Timeline timeline, Event event);
    void removeEvent(Timeline timelineId, Event event);
    void addComment(Timeline timeline, Comment comment);
    void removeComment(Timeline timeline, Comment comment);
    void deleteTimeline(Timeline id);
    List<Timeline> getAllTimelines();
    List<Timeline> getAllTimelinesBetweenDates(LocalDate from, LocalDate to);
    Timeline getTimelineById(Long id);
    Timeline getTimelineByName(String name);
}
