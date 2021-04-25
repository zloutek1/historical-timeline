package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.service.TimelineService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Tomáš Ljutenko
 */
@Service
@Transactional
public class TimelineFacadeImpl implements TimelineFacade {

    private final TimelineService timelineService;

    public TimelineFacadeImpl(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @Override
    public Long createTimeline(TimelineCreateDTO timeline) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addComment(Long timelineId, CommentCreateDTO comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeComment(Long timelineId, Long commentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteTimeline(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TimelineDTO> getAllTimelines() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TimelineDTO> getAllTimelinesBetweenDates(Date from, Date to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimelineDTO getTimelineById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimelineDTO getTimelineByName(String name) {
        throw new UnsupportedOperationException();
    }
}
