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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

    private Timeline timeline;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void finish() throws Exception {
        closeable.close();
    }

    @BeforeMethod
    public void prepareTimeline() {
        timeline = new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null);
    }

    @Test
    public void create_givenTimeline_daoCreates() {
        timelineService.create(timeline);
        verify(timelineDao).create(timeline);
    }

    @Test
    public void create_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.create(null));
    }

    @Test
    public void delete_givenTimeline_daoDeletes() {
        timelineService.delete(timeline);
        verify(timelineDao).delete(timeline);
    }

    @Test
    public void delete_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.delete(null));
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
        assertThrows(IllegalArgumentException.class, () -> timelineService.addEvent(timeline, null));
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
        assertThrows(IllegalArgumentException.class, () -> timelineService.addComment(timeline, null));
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
        Timeline t1 = new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null);
        Timeline t2 = new Timeline("T2", LocalDate.of(1995, 11, 22), LocalDate.of(1996,12, 1), null);
        Timeline t3 = new Timeline("T3", LocalDate.of(1650, 2, 28), LocalDate.of(1950, 3, 4), null);

        timelineService.create(t1);
        timelineService.create(t2);
        timelineService.create(t2);

        assertThat(timelineService.getAll()).containsExactlyInAnyOrder(t1, t2, t3);
    }

    @Test
    public void getAllBetweenDates_withOneEventContainedExactly_returnsThatOne() {
        List<Timeline> timelines = new ArrayList<>();
        timelines.add(new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null));
        Timeline t2 = new Timeline("T2", LocalDate.of(1995, 11, 22), LocalDate.of(1996,12, 1), null);
        timelines.add(t2);
        timelines.add(new Timeline("T3", LocalDate.of(1650, 2, 28), LocalDate.of(1950, 3, 4), null));

        when(timelineDao.findAll()).thenReturn(timelines);

        LocalDate fromDate = LocalDate.of(1900, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);
        assertThat(timelineService.getAllBetweenDates(fromDate, toDate)).containsExactlyInAnyOrder(t2);
    }

    @Test
    public void getById_givenValidId_returnsTimeline() {
        timelineService.getById(123L);
        verify(timelineDao).findById(123L);
    }

    @Test
    public void getById_givenNullId_throws() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.getById(null));
    }

    @Test
    public void getByName_givenValidName_returnsTimeline() {
        timelineService.getByName("some name");
        verify(timelineDao).findByName("some name");
    }

    @Test
    public void getByName_givenNullName_throws() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.getByName(null));
    }

}
