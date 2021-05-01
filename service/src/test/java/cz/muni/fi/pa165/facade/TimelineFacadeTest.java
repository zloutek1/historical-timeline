package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.StudyGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tomáš Ljutenko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TimelineService timelineService;

    @Mock
    private EventService eventService;

    @Mock
    private StudyGroupService studyGroupService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private TimelineFacadeImpl timelineFacade = new TimelineFacadeImpl();

    private AutoCloseable closeable;

    private Timeline timeline;
    private TimelineDTO timelineDTO;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        timeline = new Timeline(
                "Timeline name",
                LocalDate.of(2020, 2, 10),
                LocalDate.of(2020, 3, 15),
                null);
        timeline.setId(11L);

        timelineDTO = new TimelineDTO();
        timelineDTO.setId(11L);
        timelineDTO.setName("Timeline name");
        timelineDTO.setFromDate(LocalDate.of(2020, 2, 10));
        timelineDTO.setToDate(LocalDate.of(2020, 3, 15));
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void create_givenValidDTO_callsCreateAndReturnsValidId() {
        var createDTO = new TimelineCreateDTO();
        createDTO.setName("Timeline name");
        createDTO.setFromDate(LocalDate.of(2020, 2, 10));
        createDTO.setToDate(LocalDate.of(2020, 3, 15));

        when(beanMappingService.mapTo(createDTO, Timeline.class)).thenReturn(timeline);

        timelineFacade.create(createDTO);
        verify(timelineService).create(timeline);
    }

    @Test
    public void update_givenValidDTO_setsNonNullParametersAndCallsUpdate() {
        var updateDTO = new TimelineUpdateDTO();
        updateDTO.setId(1L);
        updateDTO.setName("New timeline name");
        updateDTO.setFromDate(LocalDate.of(2011, 5, 13));

        var expected = new Timeline(
                "New timeline name",
                LocalDate.of(2011, 5, 13),
                LocalDate.of(2020, 3, 15),
                null);

        when(timelineService.findById(1L)).thenReturn(Optional.of(timeline));
        timelineFacade.update(updateDTO);
        verify(timelineService).update(expected);
    }

    @Test
    public void delete_givenValidId_callsServiceDelete() {
        when(timelineService.findById(123L)).thenReturn(Optional.of(timeline));
        timelineFacade.delete(123L);
        verify(timelineService).delete(timeline);
    }

    @Test
    public void setStudyGroup_givenValidStudyGroup_setsStudyGroupAndCallsUpdate() {
        var studyGroup = new StudyGroup("Group name");
        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timeline));
        when(studyGroupService.findById(anyLong())).thenReturn(Optional.of(studyGroup));

        var expected = new Timeline(
                "Timeline name",
                LocalDate.of(2020, 2, 10),
                LocalDate.of(2020, 3, 15),
                studyGroup
        );
        timelineFacade.setStudyGroup(timeline.getId(), studyGroup.getId());
        verify(timelineService).update(expected);
    }

    @Test
    public void removeStudyGroup_givenValidStudyGroup_setsStudyGroupAndCallsUpdate() {
        var studyGroup = new StudyGroup("Group name");
        var timelineWithStudyGroup = new Timeline(
                "Timeline name",
                LocalDate.of(2020, 2, 10),
                LocalDate.of(2020, 3, 15),
                studyGroup
        );
        timelineWithStudyGroup.setId(16L);

        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timeline));
        when(studyGroupService.findById(anyLong())).thenReturn(Optional.of(studyGroup));

        timelineFacade.removeStudyGroup(timelineWithStudyGroup.getId());
        verify(timelineService).update(timeline);
    }

    @Test
    public void addEvent_givenValidEvent_addsEventAndCallsUpdate() {
        var event = new Event(
                "Event name",
                LocalDate.of(2020, 12, 12),
                "Paris",
                "no description",
                null);

        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timeline));
        when(eventService.getById(anyLong())).thenReturn(Optional.of(event));

        timelineFacade.addEvent(timeline.getId(), event.getId());
        verify(timelineService).update(timeline);
    }

    @Test
    public void removeEvent_givenValidEvent_removesEventAndCallsUpdate() {
        var event = new Event(
                "Event name",
                LocalDate.of(2020, 12, 12),
                "Paris",
                "no description",
                null);

        var timelineWithEvent = new Timeline(
                "Timeline name",
                LocalDate.of(2020, 2, 10),
                LocalDate.of(2020, 3, 15),
                null
        );
        timelineWithEvent.addEvent(event);

        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timeline));
        when(eventService.getById(anyLong())).thenReturn(Optional.of(event));

        timelineFacade.removeEvent(timeline.getId(), event.getId());
        verify(timelineService).update(timeline);
    }

    @Test
    public void getAll_withOneTimeline_returnsAll() {
        timelineFacade.findAll();
        verify(timelineService).findAll();
    }

    @Test
    public void getAllBetweenDates_withValidDates_returnsAllMatching() {
        var fromDate = LocalDate.of(2020, 2, 10);
        var toDate = LocalDate.of(2020, 3, 15);

        var listOfTimelines = new ArrayList<Timeline>() {{ add(timeline); }};
        var listOfTimelinesDTO = new ArrayList<TimelineDTO>() {{ add(timelineDTO); }};

        when(timelineService.findAllBetweenDates(fromDate, toDate)).thenReturn(listOfTimelines);
        when(beanMappingService.mapTo(listOfTimelines, TimelineDTO.class)).thenReturn(listOfTimelinesDTO);

        var actual = timelineFacade.findAllBetweenDates(fromDate, toDate);
        verify(timelineService).findAllBetweenDates(fromDate, toDate);

        assertThat(actual).containsExactly(timelineDTO);
    }

    @Test
    public void findById_givenValidId_returnsFoundTimeline() {
        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timeline));
        when(beanMappingService.mapTo(timeline, TimelineDTO.class)).thenReturn(timelineDTO);

        var actual = timelineFacade.findById(123L);
        verify(timelineService).findById(123L);

        assertThat(actual).contains(timelineDTO);
    }

    @Test
    public void findByName_givenValidName_returnsFoundTimeline() {
        when(timelineService.findByName(anyString())).thenReturn(Optional.of(timeline));
        when(beanMappingService.mapTo(timeline, TimelineDTO.class)).thenReturn(timelineDTO);

        var actual = timelineFacade.findByName("Timeline name");
        verify(timelineService).findByName("Timeline name");

        assertThat(actual).contains(timelineDTO);
    }
}
