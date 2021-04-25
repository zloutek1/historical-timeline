package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
public interface TimelineFacade {
    Long create(TimelineCreateDTO timeline);
    void delete(Long id);
    
    void addEvent(Long timelineId, Long eventId);
    void removeEvent(Long timelineId, Long eventId);
    void addComment(Long timelineId, CommentCreateDTO comment);
    void removeComment(Long timelineId, Long commentId);
    
    List<TimelineDTO> getAll();
    List<TimelineDTO> getAllBetweenDates(LocalDate from, LocalDate to);
    Optional<TimelineDTO> findById(Long id);
    Optional<TimelineDTO> findByName(String name);
}
