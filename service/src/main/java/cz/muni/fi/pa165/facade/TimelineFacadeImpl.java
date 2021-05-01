package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.StudyGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import org.dozer.inject.Inject;
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

    @Inject
    private EventService eventService;

    @Inject
    private StudyGroupService studyGroupService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(TimelineCreateDTO timeline) {
        Timeline mappedTimeline = beanMappingService.mapTo(timeline, Timeline.class);
        timelineService.create(mappedTimeline);
        return mappedTimeline.getId();
    }

    @Override
    public void update(TimelineUpdateDTO timelineDto) {
        Timeline timeline = timelineService.findById(timelineDto.getId())
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineDto.getId() + " found."));
        if (timelineDto.getName() != null) {
            timeline.setName(timelineDto.getName());
        }
        if (timelineDto.getFromDate() != null) {
            timeline.setFromDate(timelineDto.getFromDate());
        }
        if (timelineDto.getToDate() != null) {
            timeline.setToDate(timelineDto.getToDate());
        }
    }

    @Override
    public void delete(Long id) {
        Timeline timeline = timelineService.findById(id)
                .orElseThrow(() -> new ServiceException("No timeline with id " + id + " found."));
        timelineService.delete(timeline);
    }

    @Override
    public void setStudyGroup(Long timelineId, Long studyGroupId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineId + " found."));
        StudyGroup studyGroup = studyGroupService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No study group with id " + studyGroupId + " found."));
        timeline.setStudyGroup(studyGroup);
    }

    @Override
    public void removeStudyGroup(Long timelineId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineId + " found."));
        timeline.setStudyGroup(null);
    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineId + " found."));
        Event event = eventService.getById(timelineId)
                .orElseThrow(() -> new ServiceException("No event with id " + eventId + " found."));
        timeline.addEvent(event);
    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {
        Timeline timeline = timelineService.findById(timelineId)
                .orElseThrow(() -> new ServiceException("No timeline with id " + timelineId + " found."));
        Event event = eventService.getById(timelineId)
                .orElseThrow(() -> new ServiceException("No event with id " + eventId + " found."));
        timeline.removeEvent(event);
    }

    @Override
    public List<TimelineDTO> findAll() {
        return beanMappingService.mapTo(timelineService.findAll(), TimelineDTO.class);
    }

    @Override
    public List<TimelineDTO> findAllBetweenDates(LocalDate from, LocalDate to) {
        return beanMappingService.mapTo(timelineService.findAllBetweenDates(from, to), TimelineDTO.class);
    }

    @Override
    public Optional<TimelineDTO> findById(Long id) {
        Optional<Timeline> timeline = timelineService.findById(id);
        if( timeline.isEmpty() )
            return Optional.empty();
        TimelineDTO mappedTimeline = beanMappingService.mapTo(timeline.get(), TimelineDTO.class);
        return Optional.of(mappedTimeline);
    }

    @Override
    public Optional<TimelineDTO> findByName(String name) {
        Optional<Timeline> timeline = timelineService.findByName(name);
        if( timeline.isEmpty() )
            return Optional.empty();
        TimelineDTO mappedTimeline = beanMappingService.mapTo(timeline.get(), TimelineDTO.class);
        return Optional.of(mappedTimeline);
    }
}
