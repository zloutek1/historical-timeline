package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;


/**
 * @author Eva Krajíková
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class EventServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EventDao eventDao;

    @Inject
    @InjectMocks
    private EventService eventService;

    private AutoCloseable closeable;

    private Event event1;
    private Event event2;
    private Event event3;
    private final List<Event> events = new ArrayList<>();

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @BeforeMethod
    public void setEvents(){
        event1 = new Event(
                "E1",
                LocalDate.of(2020,1,28),
                "Slovakia",
                "",
                "");

        event2 = new Event(
                "E2",
                LocalDate.of(2020,2,2),
                "Poland",
                "5",
                "");

        event3 = new Event(
                "E3",
                LocalDate.of(2020,5,12),
                "Slovakia",
                "2 5 8",
                "");

        events.add(event1);
        events.add(event2);
        events.add(event3);
    }

    @Test
    public void create_Event_daoCreate() {
        eventService.create(event1);
        verify(eventDao).create(event1);
    }

    @Test
    public void create_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.create(null));
    }

    @Test
    public void create_failingDatabase_throws(){
        doThrow(mock(DataAccessException.class)).when(eventDao).create(any(Event.class));
        assertThrows(ServiceException.class, () -> eventService.create(event1));
    }

    @Test
    public void delete_Event_daoCreate() {
        eventService.delete(event1);
        verify(eventDao).delete(event1);
    }

    @Test
    public void delete_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.delete(null));
    }

    @Test
    public void delete_failingDatabase_throws(){
        doThrow(mock(DataAccessException.class)).when(eventDao).delete(any(Event.class));
        assertThrows(ServiceException.class, () -> eventService.delete(event1));
    }

    @Test
    public void addTimeline_Timeline_EventContains(){
        Timeline t = new Timeline();

        eventService.addTimeline(event1,t);
        assertThat(event1.getTimelines()).containsOnly(t);
    }

    @Test
    public void addTimeline_TimelineTwice_throws(){
        Timeline t = new Timeline();

        eventService.addTimeline(event1,t);
        assertThrows(ServiceException.class, () -> eventService.addTimeline(event1, t));
    }

    @Test
    public void addTimeline_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.addTimeline(event1, null));
    }

    @Test
    public void addTimeline_failingDatabase_throws(){
        Timeline t = new Timeline();
        Event e = mock(Event.class);

        doThrow(mock(DataAccessException.class)).when(e).addTimeline(any(Timeline.class));
        assertThrows(ServiceException.class, () -> eventService.addTimeline(e, t));
    }

    @Test
    public void removeTimeline_Timeline_EventDoesNotContain(){
        Timeline t = new Timeline();

        eventService.addTimeline(event1,t);
        eventService.removeTimeline(event1,t);

        assertThat(event1.getTimelines()).doesNotContain(t);
    }

    @Test
    public void removeTimeline_unknownTimeline_throws(){
        Timeline t = new Timeline();

        assertThrows(ServiceException.class, () -> eventService.removeTimeline(event1, t));
    }

    @Test
    public void removeTimeline_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.removeTimeline(event1, null));
    }

    @Test
    public void removeTimeline_failingDatabase_throws(){
        Timeline t = new Timeline();
        Event e = mock(Event.class);

        doThrow(mock(DataAccessException.class)).when(e).removeTimeline(any(Timeline.class));
        assertThrows(ServiceException.class, () -> eventService.removeTimeline(e, t));
    }

    @Test
    public void findById_EventId_daoFindById(){
        eventService.findById(1L);
        verify(eventDao).findById(1L);
    }

    @Test
    public void findById_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.findById(null));
    }

    @Test
    public void findById_failingDatabase_throws(){
        doThrow(mock(DataAccessException.class)).when(eventDao).findById(anyLong());
        assertThrows(ServiceException.class, () -> eventService.findById(1L));
    }

    @Test
    public void findByName_EventName_daoFindByName(){
        eventService.findByName("E");
        verify(eventDao).findByName("E");
    }

    @Test
    public void findByName_null_throws(){
        assertThrows(IllegalArgumentException.class, () -> eventService.findByName(null));
    }

    @Test
    public void findByName_failingDatabase_throws(){
        doThrow(mock(DataAccessException.class)).when(eventDao).findByName(anyString());
        assertThrows(ServiceException.class, () -> eventService.findByName("E"));
    }

    @Test
    public void findInRangeEvents_range_twoEventsInRange(){
        when(eventDao.findAll()).thenReturn(events);

        LocalDate since = LocalDate.of(2020,1,27);
        LocalDate to = LocalDate.of(2020,2,3);

        assertThat (eventService.findAllInRange(since, to)).contains(event1, event2);
    }

    @Test
    public void findByLocationEvents_location_twoEventsAtLocation(){
        when(eventDao.findAll()).thenReturn(events);

        assertThat (eventService.findByLocation("Slovakia")).contains(event1, event3);
    }

    @Test
    public void findByDescription_description_twoEventsWithDescription(){
        when(eventDao.findAll()).thenReturn(events);

        assertThat (eventService.findByDescription("5")).contains(event2, event3);
    }

    @Test
    public void findAllEvents_threeEvents(){
        when(eventDao.findAll()).thenReturn(events);

        assertThat (eventService.findAllEvents()).contains(event1, event2, event3);
    }

    @Test
    public void findAllEvents_failingDatabase_throws(){
        doThrow(mock(DataAccessException.class)).when(eventDao).findAll();
        assertThrows(ServiceException.class, () -> eventService.findAllEvents());
    }
}
