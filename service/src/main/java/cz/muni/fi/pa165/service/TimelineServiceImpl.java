package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.dozer.inject.Inject;
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
        timelineDao.create(timeline);
    }

    @Override
    public void update(@NonNull Timeline timeline) {
        timelineDao.update(timeline);
    }

    @Override
    public void delete(@NonNull Timeline timeline) {
        timelineDao.delete(timeline);
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
    public List<Timeline> getAll() {
        return timelineDao.findAll();
    }

    @Override
    public List<Timeline> getAllBetweenDates(@NonNull LocalDate from, @NonNull LocalDate to) {
        return timelineDao
                .findAll()
                .stream()
                .filter(timeline -> timeline.getFromDate().isAfter(from) &&
                                    timeline.getToDate().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Timeline> getById(@NonNull Long id) {
        return timelineDao.findById(id);
    }

    @Override
    public Optional<Timeline> getByName(@NonNull String name) {
        return timelineDao.findByName(name);
    }
}
