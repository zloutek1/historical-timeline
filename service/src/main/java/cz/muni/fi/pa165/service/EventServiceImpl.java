package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
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
 * @author Eva Krajíková
 */

@Service
public class EventServiceImpl implements EventService{

    @Inject
    private EventDao eventDao;

    @Override
    public void create(@NonNull Event event) {
        eventDao.create(event);
    }

    @Override
    public void addTimeline(@NonNull Event event,@NonNull Timeline timeline) {
        if (event.getTimelines().contains(timeline)) {
            throw new ServiceException(
                    "Event already contains this Timeline. Event: "
                            + event.getId() + ", Timeline: "
                            + timeline.getId());
        }
        event.addTimeline(timeline);
    }

    @Override
    public void removeTimeline(@NonNull Event event,@NonNull Timeline timeline) {
        if (!event.getTimelines().contains(timeline)){
            throw new ServiceException(
                    "Event does not contain this Timeline. Event: "
                            + event.getId() + ", Timeline: "
                            + timeline.getId());
        }
        event.removeTimeline(timeline);
    }

    @Override
    public Optional<Event> getById(@NonNull Long eventId) {
        return eventDao.findById(eventId);
    }

    @Override
    public Optional<Event> getByName(@NonNull String name) {
        return eventDao.findByName(name);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.findAll();
    }

    @Override
    public List<Event> getAllInRange(LocalDate since, LocalDate to) {
        return eventDao
                .findAll()
                .stream()
                .filter(event -> event.getDate().isAfter(since) &&
                                 event.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getByLocation(String location) {
        return eventDao
                .findAll()
                .stream()
                .filter(event -> event.getLocation().equals(location))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getByDescription(String description) {
        return eventDao
                .findAll()
                .stream()
                .filter(event -> event.getDescription().contains(description))
                .collect(Collectors.toList());
    }
}
