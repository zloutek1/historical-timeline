package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

/**
 * @author Tomáš Ljutenko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TimelineDao timelineDao;

    @Autowired
    @InjectMocks
    private TimelineService timelineService;

    private AutoCloseable closeable;

    private Timeline timeline;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        timeline = new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void create_givenTimeline_callsDaoCreate() {
        timelineService.create(timeline);
        verify(timelineDao).create(timeline);
    }

    @Test
    public void create_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.create(null));
    }

    @Test
    public void create_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).create(any(Timeline.class));
        assertThatExceptionOfType(ServiceException.class)
            .isThrownBy(() -> timelineService.create(timeline))
            .withMessageContaining(timeline.toString());
    }

    @Test
    public void update_givenTimeline_callsDaoUpdate() {
        timelineService.update(timeline);
        verify(timelineDao).update(timeline);
    }

    @Test
    public void update_givenNull_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.update(null));
    }

    @Test
    public void update_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).update(any(Timeline.class));
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.update(timeline))
                .withMessageContaining(timeline.toString());
    }

    @Test
    public void delete_givenTimeline_daoDeletes() {
        timelineService.delete(timeline);
        verify(timelineDao).delete(timeline);
    }

    @Test
    public void delete_givenNull_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.delete(null));
    }

    @Test
    public void delete_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).delete(any(Timeline.class));
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.delete(timeline))
                .withMessageContaining(timeline.toString());
    }

    @Test
    public void addEvent_givenNewEvent_adds() {
        Event event = new Event("E1", LocalDate.of(2020, 4, 16), "Brno", "short description", "image.png");
        timelineService.addEvent(timeline, event);
        assertThat(timeline.getEvents()).containsExactlyInAnyOrder(event);
    }

    @Test
    public void addEvent_givenDuplicateEvent_throws() {
        Event event = new Event("E1", LocalDate.of(2020, 4, 16), "Brno", "short description", "image.png");
        timelineService.addEvent(timeline, event);
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.addEvent(timeline, event));
    }

    @Test
    public void addEvent_givenNullEvent_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.addEvent(timeline, null));
    }

    @Test
    public void removeEvent_givenExistingEvent_removes() {
        Event event = new Event("E1", LocalDate.of(2020, 4, 16), "Brno", "short description", "image.png");
        timelineService.addEvent(timeline, event);
        timelineService.removeEvent(timeline, event);
        assertThat(timeline.getEvents()).isEmpty();
    }

    @Test
    public void addComment_givenNewComment_adds() {
        Comment comment = new Comment("woah", LocalDateTime.of(LocalDate.of(2020, 6, 26), LocalTime.of(12, 55)));
        timelineService.addComment(timeline, comment);
        assertThat(timeline.getComments()).containsExactlyInAnyOrder(comment);
    }

    @Test
    public void addComment_givenDuplicateComment_throws() {
        Comment comment = new Comment("woah", LocalDateTime.of(LocalDate.of(2020, 6, 26), LocalTime.of(12, 55)));
        timelineService.addComment(timeline, comment);
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.addComment(timeline, comment));
    }

    @Test
    public void addComment_givenNullComment_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.addComment(timeline, null));
    }

    @Test
    public void removeComment_givenExistingComment_removes() {
        Comment comment = new Comment("woah", LocalDateTime.of(LocalDate.of(2020, 6, 26), LocalTime.of(12, 55)));
        timelineService.addComment(timeline, comment);
        timelineService.removeComment(timeline, comment);
        assertThat(timeline.getComments()).isEmpty();
    }

    @Test
    public void getAll_withThreeTimelines_returnsAll() {
        List<Timeline> timelines = new ArrayList<>();
        timelines.add(new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null));
        timelines.add(new Timeline("T2", LocalDate.of(1995, 11, 22), LocalDate.of(1996,12, 1), null));
        timelines.add(new Timeline("T3", LocalDate.of(1650, 2, 28), LocalDate.of(1950, 3, 4), null));

        when(timelineDao.findAll()).thenReturn(timelines);

        assertThat(timelineService.getAll()).containsExactlyInAnyOrderElementsOf(timelines);
    }

    @Test
    public void getAll_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).findAll();
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.getAll());
    }

    @Test
    public void getAllBetweenDates_withOneEventContainedExactly_returnsThatOne() {
        List<Timeline> timelines = new ArrayList<>();
        timelines.add(new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null));
        timelines.add(new Timeline("T2", LocalDate.of(1995, 11, 22), LocalDate.of(1996,12, 1), null));
        timelines.add(new Timeline("T3", LocalDate.of(1650, 2, 28), LocalDate.of(1950, 3, 4), null));

        when(timelineDao.findAll()).thenReturn(timelines);

        LocalDate fromDate = LocalDate.of(1900, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);
        assertThat(timelineService.getAllBetweenDates(fromDate, toDate)).containsExactlyInAnyOrder(timelines.get(1));
    }

    @Test
    public void getAllBetweenDates_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).findAll();

        LocalDate fromDate = LocalDate.of(1900, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.getAllBetweenDates(fromDate, toDate))
                .withMessageContaining(fromDate.toString())
                .withMessageContaining(toDate.toString());
    }

    @Test
    public void getById_givenValidId_returnsTimeline() {
        timelineService.getById(123L);
        verify(timelineDao).findById(123L);
    }

    @Test
    public void getById_givenNullId_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.getById(null));
    }

    @Test
    public void getById_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).findById(anyLong());
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.getById(123L))
                .withMessageContaining("123");
    }

    @Test
    public void getByName_givenValidName_returnsTimeline() {
        timelineService.getByName("some name");
        verify(timelineDao).findByName("some name");
    }

    @Test
    public void getByName_givenNullName_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> timelineService.getByName(null));
    }

    @Test
    public void getByName_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(timelineDao).findByName(anyString());
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> timelineService.getByName("myName"))
                .withMessageContaining("myName");
    }
}
