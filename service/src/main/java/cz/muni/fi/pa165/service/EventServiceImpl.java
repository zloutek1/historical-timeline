package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
        try {
            eventDao.create(event);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to create event " + event, ex);
        }
    }

    @Override
    public void delete(@NonNull Event event){
        try {
            eventDao.delete(event);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to delete event " + event, ex);
        }
    }

    @Override
    public void addTimeline(@NonNull Event event,@NonNull Timeline timeline) {
        try {
            if (event.getTimelines().contains(timeline)) {
                throw new ServiceException(
                        "Event already contains this Timeline. Event: "
                                + event.getId() + ", Timeline: "
                                + timeline.getId());
            }
            event.addTimeline(timeline);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to add Timeline " + timeline + " to Event " + event, ex);
        }
    }

    @Override
    public void removeTimeline(@NonNull Event event,@NonNull Timeline timeline) {
        try {
            if (!event.getTimelines().contains(timeline)) {
                throw new ServiceException(
                        "Event does not contain this Timeline. Event: "
                                + event.getId() + ", Timeline: "
                                + timeline.getId());
            }
            event.removeTimeline(timeline);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to remove Timeline " + timeline + " from Event " + event, ex);
        }
    }

    @Override
    public Optional<Event> findById(@NonNull Long eventId) {
        try {
            return eventDao.findById(eventId);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find Event by Id "+ eventId, ex);
        }
    }

    @Override
    public Optional<Event> findByName(@NonNull String name) {
        try {
            return eventDao.findByName(name);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find Event by name "+ name, ex);
        }
    }

    @Override
    public List<Event> findAllInRange(LocalDate since, LocalDate to) {
        try {
            return eventDao
                    .findAll()
                    .stream()
                    .filter(event -> event.getDate().isAfter(since) &&
                            event.getDate().isBefore(to))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find Events since " + since + " to " + to, ex);
        }
    }

    @Override
    public List<Event> findByLocation(String location) {
        try {
            return eventDao
                    .findAll()
                    .stream()
                    .filter(event -> event.getLocation().equals(location))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find Events by location "+ location, ex);
        }
    }

    @Override
    public List<Event> findByDescription(String description) {
        try {
            return eventDao
                    .findAll()
                    .stream()
                    .filter(event -> event.getDescription().contains(description))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find Events by description "+ description, ex);
        }
    }

    @Override
    public List<Event> findAllEvents() {
        try {
            return eventDao.findAll();
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to find all Events", ex);
        }
    }
}
