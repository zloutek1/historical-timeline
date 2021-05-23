package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
public interface TimelineFacade {
    /**
     * Store a timeline
     * @param timeline information regarding timeline creation
     * @return id of the created timeline
     */
    Long createTimeline(TimelineCreateDTO timeline);

    /**
     * Update a timeline
     * @param timeline information that will be updated if set
     */
    void updateTimeline(TimelineUpdateDTO timeline);

    /**
     * Delete a timeline
     * @param id of the timeline
     */
    void deleteTimeline(Long id);

    /**
     * Add an event to a study group
     * @param timelineId of the timeline to be updated
     * @param eventId of the event to be added
     */
    void addEvent(Long timelineId, Long eventId);

    /**
     * Remove an event from a study group
     * @param timelineId of the timeline to be updated
     * @param eventId of the event to be removed
     */
    void removeEvent(Long timelineId, Long eventId);

    /**
     * @return all timelines
     */
    List<TimelineDTO> findAll();

    /**
     * @param from starting date
     * @param to ending date
     * @return all timelines between dates
     */
    List<TimelineDTO> findAllBetweenDates(LocalDate from, LocalDate to);

    /**
     * @param id of the timeline
     * @return timeline if the timeline is found else return empty
     */
    Optional<TimelineDTO> findById(Long id);

    /**
     * @param name name of the timeline
     * @return timeline if the timeline is found else return empty
     */
    Optional<TimelineDTO> findByName(String name);
}
