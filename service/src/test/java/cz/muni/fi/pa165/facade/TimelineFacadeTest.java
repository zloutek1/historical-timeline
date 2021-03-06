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
    private final TimelineFacadeImpl timelineFacade = new TimelineFacadeImpl();

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
    public void create_givenValidDTO_createsTimeline() {
        var studyGroup = new StudyGroup("Study group name");
        studyGroup.setId(555L);

        var createDTO = new TimelineCreateDTO();
        createDTO.setName("Timeline name");
        createDTO.setFromDate(LocalDate.of(2020, 2, 10));
        createDTO.setToDate(LocalDate.of(2020, 3, 15));
        createDTO.setStudyGroupId(studyGroup.getId());

        when(studyGroupService.findById(555L)).thenReturn(Optional.of(studyGroup));
        when(beanMappingService.mapTo(createDTO, Timeline.class)).thenReturn(timeline);

        timelineFacade.createTimeline(createDTO);

        timeline.setStudyGroup(studyGroup);
        verify(timelineService).create(timeline);

        assertThat(studyGroup.getTimelines()).containsExactly(timeline);
    }

    @Test
    public void update_givenValidDTO_setsNonNullParametersAndCallsUpdate() {
        var updateDTO = new TimelineUpdateDTO();
        updateDTO.setId(timeline.getId());
        updateDTO.setName("New timeline name");
        updateDTO.setFromDate(LocalDate.of(2011, 5, 13));

        var expected = new Timeline(
                "New timeline name",
                LocalDate.of(2011, 5, 13),
                LocalDate.of(2020, 3, 15),
                null);
        expected.setId(timeline.getId());

        when(timelineService.findById(timeline.getId())).thenReturn(Optional.of(timeline));
        timelineFacade.updateTimeline(updateDTO);
        assertThat(timeline).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void delete_givenValidId_callsServiceDelete() {
        when(timelineService.findById(123L)).thenReturn(Optional.of(timeline));
        timelineFacade.deleteTimeline(123L);
        verify(timelineService).delete(timeline);
    }

    @Test
    public void addEvent_givenValidEvent_addsEventAndCallsUpdate() {
        var event = new Event(
                "Event name",
                LocalDate.of(2020, 12, 12),
                "Paris",
                "no description",
                null);
        event.setId(123L);

        when(timelineService.findById(timeline.getId())).thenReturn(Optional.of(timeline));
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));

        timelineFacade.addEvent(timeline.getId(), event.getId());
        assertThat(timeline.getEvents()).contains(event);
    }

    @Test
    public void removeEvent_givenValidEvent_removesEventAndCallsUpdate() {
        var event = new Event(
                "Event name",
                LocalDate.of(2020, 12, 12),
                "Paris",
                "no description",
                null);
        event.setId(789L);

        var timelineWithEvent = new Timeline(
                "Timeline name",
                LocalDate.of(2020, 2, 10),
                LocalDate.of(2020, 3, 15),
                null
        );
        timelineWithEvent.setId(456L);
        timelineWithEvent.addEvent(event);

        when(timelineService.findById(anyLong())).thenReturn(Optional.of(timelineWithEvent));
        when(eventService.findById(anyLong())).thenReturn(Optional.of(event));

        timelineFacade.removeEvent(timelineWithEvent.getId(), event.getId());
        assertThat(timelineWithEvent.getEvents()).doesNotContain(event);
    }

    @Test
    public void findAll_withOneTimeline_returnsAll() {
        timelineFacade.findAll();
        verify(timelineService).findAll();
    }

    @Test
    public void findAllBetweenDates_withValidDates_returnsAllMatching() {
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
