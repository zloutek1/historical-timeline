package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Eva Krajíková
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class EventFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EventService eventService;

    @Mock
    private TimelineService timelineService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final EventFacade eventFacade = new EventFacadeImpl();

    private AutoCloseable closeable;

    private Event event;
    private EventDTO eventDTO;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        event = new Event(
                "Event",
                LocalDate.of(2020,1,28),
                "Slovakia",
                "5",
                null);
        event.setId(1L);

        eventDTO = new EventDTO();
        eventDTO.setName("Event");
        eventDTO.setDate(LocalDate.of(2020,1,28));
        eventDTO.setLocation("Slovakia");
        eventDTO.setDescription("5");
        eventDTO.setImage(null);
        eventDTO.setId(1L);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void createEvent_validDTO_createEvent(){
        EventCreateDTO createDTO = new EventCreateDTO();
        createDTO.setName("Event");

        Event mappedEvent = new Event();
        mappedEvent.setName("Event");

        when(beanMappingService.mapTo(createDTO, Event.class)).thenReturn(mappedEvent);

        eventFacade.createEvent(createDTO);
        verify(eventService).create(mappedEvent);
    }

    @Test
    public void updateEvent_validDTO_updateEvent(){
        Event updatedEvent = new Event(
                "Event2",
                LocalDate.of(2020,2,28),
                "Poland",
                "2",
                null);
        updatedEvent.setId(event.getId());

        EventDTO updatedDTO = new EventDTO();
        updatedDTO.setName("Event2");
        updatedDTO.setDate(LocalDate.of(2020,2,28));
        updatedDTO.setLocation("Poland");
        updatedDTO.setDescription("2");
        updatedDTO.setImage(null);
        updatedDTO.setId(eventDTO.getId());

        assertThat(event).isNotEqualTo(updatedEvent);
        when(eventService.findById(eventDTO.getId())).thenReturn(Optional.of(event));
        eventFacade.updateEvent(updatedDTO);
        assertThat(event).isEqualTo(updatedEvent);
    }

    @Test
    public void delete_validId_deleteEvent() {
        when(eventService.findById(eventDTO.getId())).thenReturn(Optional.of(event));
        eventFacade.deleteEvent(eventDTO.getId());
        verify(eventService).delete(event);
    }

    @Test void addTimeline_validTimelineId_addTimeline(){
        Timeline timeline = new Timeline();
        timeline.setId(2L);
        timeline.setFromDate(LocalDate.of(2020,1,27));
        timeline.setToDate(LocalDate.of(2020,1,29));

        when(eventService.findById(event.getId())).thenReturn(Optional.ofNullable(event));
        when(timelineService.findById(timeline.getId())).thenReturn(Optional.of(timeline));

        eventFacade.addTimeline(event.getId(), timeline.getId());
        assertThat(event.getTimelines()).contains(timeline);
    }

    @Test
    public void removeTimeline_validTimelineId_removeTimeline(){
        Timeline timeline = new Timeline();
        timeline.setId(2L);

        event.addTimeline(timeline);

        when(eventService.findById(event.getId())).thenReturn(Optional.ofNullable(event));
        when(timelineService.findById(timeline.getId())).thenReturn(Optional.of(timeline));

        eventFacade.removeTimeline(event.getId(), timeline.getId());
        assertThat(event.getTimelines()).doesNotContain(timeline);
    }

    @Test
    public void findById_validEventId_Event(){
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        when(beanMappingService.mapTo(event, EventDTO.class)).thenReturn(eventDTO);

        Optional<EventDTO> found = eventFacade.findById(event.getId());
        verify(eventService).findById(event.getId());

        assertThat(found).contains(eventDTO);
    }

    @Test
    public void findByName_validEventName_Event(){
        when(eventService.findByName("Event")).thenReturn(Optional.of(event));
        when(beanMappingService.mapTo(event, EventDTO.class)).thenReturn(eventDTO);

        Optional<EventDTO> found = eventFacade.findByName("Event");
        verify(eventService).findByName("Event");

        assertThat(found).contains(eventDTO);
    }

    @Test
    public void findAllInRange_sinceTo_returnsEvent(){
        LocalDate since = LocalDate.of(2020,1,27);
        LocalDate to = LocalDate.of(2020,2,3);

        List<Event> events = List.of(event);
        List<EventDTO> eventDTOs = List.of(eventDTO);

        when(eventService.findAllInRange(since, to)).thenReturn(events);
        when(beanMappingService.mapTo(events, EventDTO.class)).thenReturn(eventDTOs);

        List<EventDTO> found = eventFacade.findAllInRange(since, to);
        verify(eventService).findAllInRange(since, to);

        assertThat(found).contains(eventDTO);
    }

    @Test
    public void findByLocation_validEventName_Event(){
        List<Event> events = List.of(event);
        List<EventDTO> eventDTOs = List.of(eventDTO);

        when(eventService.findByLocation("Slovakia")).thenReturn(events);
        when(beanMappingService.mapTo(events, EventDTO.class)).thenReturn(eventDTOs);

        List<EventDTO> found = eventFacade.findByLocation("Slovakia");
        verify(eventService).findByLocation("Slovakia");

        assertThat(found).contains(eventDTO);
    }

    @Test
    public void findByDescription_validEventName_Event(){
        List<Event> events = List.of(event);
        List<EventDTO> eventDTOs = List.of(eventDTO);

        when(eventService.findByDescription("5")).thenReturn(events);
        when(beanMappingService.mapTo(events, EventDTO.class)).thenReturn(eventDTOs);

        List<EventDTO> found = eventFacade.findByDescription("5");
        verify(eventService).findByDescription("5");

        assertThat(found).contains(eventDTO);
    }

    @Test
    public void findAllTimelines_oneTimeline_findAll() {
        Timeline timeline = new Timeline();
        timeline.setId(2L);

        TimelineDTO timelineDTO = new TimelineDTO();
        timelineDTO.setId(2L);

        event.addTimeline(timeline);

        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        when(beanMappingService.mapTo(anyCollection(), eq(TimelineDTO.class))).thenReturn(List.of(timelineDTO));
        List<TimelineDTO> timelines = eventFacade.findTimelines(event.getId());

        assertThat(timelines).containsOnly(timelineDTO);
    }

    @Test
    public void findAllEvents_oneEvent_findAll() {
        List<Event> events = List.of(event);
        List<EventDTO> eventDTOs = List.of(eventDTO);

        when(eventService.findAllEvents()).thenReturn(events);
        when(beanMappingService.mapTo(events, EventDTO.class)).thenReturn(eventDTOs);

        List<EventDTO> found = eventFacade.findAllEvents();
        verify(eventService).findAllEvents();

        assertThat(found).containsOnly(eventDTO);
    }

}
