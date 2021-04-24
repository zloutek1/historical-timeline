package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Timeline;

import java.time.LocalDate;
import java.util.List;


public interface TimelineService {
    void createTimeline(Timeline timeline);
    void addEvent(Long timelineId, Long eventId);
    void removeEvent(Long timelineId, Long eventId);
    void addComment(Long timelineId, Long commentId);
    void removeComment(Long timelineId, Long commentId);
    void deleteTimeline(Long id);
    List<Timeline> getAllTimelines();
    List<Timeline> getAllTimelinesBetweenDates(LocalDate from, LocalDate to);
    Timeline getTimelineById(Long id);
    Timeline getTimelineByName(String name);
}
