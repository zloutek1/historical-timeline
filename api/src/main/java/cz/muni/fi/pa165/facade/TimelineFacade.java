package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;

import java.util.Date;
import java.util.List;

public interface TimelineFacade {
    Long createTimeline(TimelineCreateDTO t);
    void addEvent(Long timelineId, Long eventId);
    void removeEvent(Long timelineId, Long eventId);
    void addComment(Long timelineId, Long commentId);
    void removeComment(Long timelineId, Long commentId);
    void deleteTimeline(Long id);

    List<TimelineDTO> getAllTimelines();
    List<TimelineDTO> getAllTimelinesBetweenDates(Date from, Date to);
    TimelineDTO getTimelineById(Long id);
    TimelineDTO getTimelineByName(String name);
}
