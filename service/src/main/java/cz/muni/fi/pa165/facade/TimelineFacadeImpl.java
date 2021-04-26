package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.TimelineService;
import org.apache.commons.lang3.NotImplementedException;
import org.dozer.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
@Service
@Transactional
public class TimelineFacadeImpl implements TimelineFacade {

    @Inject
    private TimelineService timelineService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long create(TimelineCreateDTO timeline) {
        Timeline mappedTimeline = beanMappingService.mapTo(timeline, Timeline.class);
        timelineService.create(mappedTimeline);
        return mappedTimeline.getId();
    }

    @Override
    public void delete(Long id) {
        Timeline timeline = timelineService.getById(id).orElseThrow(IllegalArgumentException::new);
        timelineService.delete(timeline);
    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {
        throw new NotImplementedException();
    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {
        throw new NotImplementedException();
    }

    @Override
    public void addComment(Long timelineId, CommentCreateDTO comment) {
        throw new NotImplementedException();
    }

    @Override
    public void removeComment(Long timelineId, Long commentId) {
        throw new NotImplementedException();
    }

    @Override
    public List<TimelineDTO> getAll() {
        return beanMappingService.mapTo(timelineService.getAll(), TimelineDTO.class);
    }

    @Override
    public List<TimelineDTO> getAllBetweenDates(LocalDate from, LocalDate to) {
        return beanMappingService.mapTo(timelineService.getAllBetweenDates(from, to), TimelineDTO.class);
    }

    @Override
    public Optional<TimelineDTO> findById(Long id) {
        Optional<Timeline> timeline = timelineService.getById(id);
        if( timeline.isEmpty() )
            return Optional.empty();
        TimelineDTO mappedTimeline = beanMappingService.mapTo(timeline.get(), TimelineDTO.class);
        return Optional.of(mappedTimeline);
    }

    @Override
    public Optional<TimelineDTO> findByName(String name) {
        Optional<Timeline> timeline = timelineService.getByName(name);
        if( timeline.isEmpty() )
            return Optional.empty();
        TimelineDTO mappedTimeline = beanMappingService.mapTo(timeline.get(), TimelineDTO.class);
        return Optional.of(mappedTimeline);
    }
}
