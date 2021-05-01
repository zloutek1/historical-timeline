package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.dozer.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tomáš Ljutenko
 */
@Service
public class TimelineServiceImpl implements TimelineService {

    @Inject
    private TimelineDao timelineDao;

    @Override
    public void create(@NonNull Timeline timeline) {
        try {
            timelineDao.create(timeline);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to create Timeline " + timeline, ex);
        }
    }

    @Override
    public void update(@NonNull Timeline timeline) {
        try {
            timelineDao.update(timeline);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to update Timeline " + timeline, ex);
        }
    }

    @Override
    public void delete(@NonNull Timeline timeline) {
        try {
            timelineDao.delete(timeline);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to delete Timeline " + timeline, ex);
        }
    }

    @Override
    public void addEvent(@NonNull Timeline timeline, @NonNull Event event) {
        if (timeline.getEvents().contains(event)) {
            throw new ServiceException(
                    "Timeline already contains this event. Product: "
                            + timeline.getId() + ", category: "
                            + event.getId());
        }
        timeline.addEvent(event);
    }

    @Override
    public void removeEvent(@NonNull Timeline timeline, @NonNull Event event) {
        timeline.removeEvent(event);
    }

    @Override
    public void addComment(@NonNull Timeline timeline, @NonNull Comment comment) {
        if (timeline.getComments().contains(comment)) {
            throw new ServiceException(
                    "Timeline already contains this comment. Product: "
                            + timeline.getId() + ", category: "
                            + comment.getId());
        }
        timeline.addComment(comment);
    }

    @Override
    public void removeComment(@NonNull Timeline timeline, @NonNull Comment comment) {
        timeline.removeComment(comment);
    }

    @Override
    public List<Timeline> findAll() {
        try {
            return timelineDao.findAll();
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to get all timelines", ex);
        }
    }

    @Override
    public List<Timeline> findAllBetweenDates(@NonNull LocalDate from, @NonNull LocalDate to) {
        try {
            return timelineDao
                    .findAll()
                    .stream()
                    .filter(timeline -> timeline.getFromDate().isAfter(from) &&
                            timeline.getToDate().isBefore(to))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to get all timelines between dates " + from + " and " + to, ex);
        }
    }

    @Override
    public Optional<Timeline> findById(@NonNull Long id) {
        try {
            return timelineDao.findById(id);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to get timeline by id " + id, ex);
        }
    }

    @Override
    public Optional<Timeline> findByName(@NonNull String name) {
        try {
            return timelineDao.findByName(name);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to get timeline by name " + name, ex);
        }
    }
}
