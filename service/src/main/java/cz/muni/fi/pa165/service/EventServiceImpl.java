package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
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
    public void createEvent(Event event) {
        eventDao.create(event);
    }

    @Override
    public void addTimeline(Event event, Timeline timeline) {
        event.addTimeline(timeline);
        eventDao.update(event);
    }

    @Override
    public void removeTimeline(Event event, Timeline timeline) {
        event.removeTimeline(timeline);
        eventDao.update(event);
    }

    @Override
    public Optional<Event> getById(Long eventId) {
        return eventDao.findById(eventId);
    }

    @Override
    public Optional<Event> getByName(String name) {
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
