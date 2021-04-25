package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.dozer.inject.Inject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimelineServiceImpl implements TimelineService {

    @Inject
    private TimelineDao timelineDao;

    @Override
    public void create(Timeline timeline) {
        timelineDao.create(timeline);
    }

    @Override
    public void addEvent(Timeline timeline, Event event) {
        if (timeline.getEvents().contains(event)) {
            throw new ServiceException(
                    "Timeline already contains this event. Product: "
                            + timeline.getId() + ", category: "
                            + event.getId());
        }
        timeline.addEvent(event);
    }

    @Override
    public void removeEvent(Timeline timeline, Event event) {
        timeline.removeEvent(event);
    }

    @Override
    public void addComment(Timeline timeline, Comment comment) {
        if (timeline.getComments().contains(comment)) {
            throw new ServiceException(
                    "Timeline already contains this comment. Product: "
                            + timeline.getId() + ", category: "
                            + comment.getId());
        }
        timeline.addComment(comment);
    }

    @Override
    public void removeComment(Timeline timeline, Comment comment) {
        timeline.removeComment(comment);
    }

    @Override
    public void delete(Timeline timeline) {
        timelineDao.delete(timeline);
    }

    @Override
    public List<Timeline> getAll() {
        return timelineDao.findAll();
    }

    @Override
    public List<Timeline> getAllBetweenDates(LocalDate from, LocalDate to) {
        return timelineDao
                .findAll()
                .stream()
                .filter(timeline -> timeline.getFromDate().isAfter(from) &&
                                    timeline.getToDate().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Timeline> getById(Long id) {
        return timelineDao.findById(id);
    }

    @Override
    public Optional<Timeline> getByName(String name) {
        return timelineDao.findByName(name);
    }
}
