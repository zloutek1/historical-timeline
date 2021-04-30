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
    Long create(TimelineCreateDTO timeline);
    void update(TimelineUpdateDTO timeline);
    void delete(Long id);

    void setStudyGroup(Long timelineId, Long studyGroupId);
    void removeStudyGroup(Long timelineId);
    void addEvent(Long timelineId, Long eventId);
    void removeEvent(Long timelineId, Long eventId);
    
    List<TimelineDTO> getAll();
    List<TimelineDTO> getAllBetweenDates(LocalDate from, LocalDate to);
    Optional<TimelineDTO> findById(Long id);
    Optional<TimelineDTO> findByName(String name);
}
