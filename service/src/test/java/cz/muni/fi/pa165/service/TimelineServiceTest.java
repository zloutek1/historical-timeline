package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entity.Timeline;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

/**
 * @author Tomáš Ljutenko
 */
@ContextConfiguration(classes = TimelineService.class)
public class TimelineServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TimelineDao timelineDao;

    @Autowired
    @InjectMocks
    private TimelineService timelineService;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void create_givenTimeline_daoCreates() {
        Timeline timeline = new Timeline("T1", LocalDate.of(2020, 1, 11), LocalDate.of(2021,6, 15), null);
        timelineService.create(timeline);
        verify(timelineDao).create(timeline);
    }


}
