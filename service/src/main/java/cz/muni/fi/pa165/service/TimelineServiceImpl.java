package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TimelineServiceImpl implements TimelineService {

    @Override
    public void createTimeline(Timeline timeline) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEvent(Timeline timeline, Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEvent(Timeline timelineId, Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addComment(Timeline timeline, Comment comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeComment(Timeline timeline, Comment comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteTimeline(Timeline timeline) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Timeline> getAllTimelines() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Timeline> getAllTimelinesBetweenDates(LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timeline getTimelineById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timeline getTimelineByName(String name) {
        throw new UnsupportedOperationException();
    }
}
