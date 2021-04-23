package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Tomáš Ljutenko
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EventDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EventDao eventDao;

    @Test
    public void get_emptyDatabase_isEmpty() {
        List<Event> rows = eventDao.findAll();
        assertThat(rows).isEmpty();
    }

    @Test
    public void insert_givenSingleValidEvent_persisted() {
        var event = new Event(
                "The Battle of Hastings",
                LocalDate.of(1066, 8, 14),
                "England",
                "between the Norman-French army of William, the Duke of Normandy, and an English army under the Anglo-Saxon King Harold",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Bayeux_Tapestry_scene57_Harold_death.jpg/300px-Bayeux_Tapestry_scene57_Harold_death.jpg");

        eventDao.create(event);
        assertNotNull(event.getId());

        List<Event> rows = eventDao.findAll();
        assertThat(rows).containsExactlyInAnyOrder(event);
    }

    @Test
    public void insert_givenMultipleValidEvents_persisted() {
        Event a = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        Event b = new Event("B", LocalDate.of(2021, 8, 22), "Slovak republic", "Some other day of the week", "https://image.gif");
        Event c = new Event("C", LocalDate.of(2023, 9, 30), "England", "None provided", null);

        var events = new ArrayList<Event>();
        events.add(a);
        events.add(b);
        events.add(c);

        for (Event event: events) {
            eventDao.create(event);
            assertNotNull(event.getId());
        }

        List<Event> rows = eventDao.findAll();
        assertThat(rows).containsExactlyInAnyOrderElementsOf(events);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void insert_withDuplicateName_throws() {
        Event a1 = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        Event a2 = new Event("A", LocalDate.of(2020, 5, 15), "Czech republic", "another event with same name", "https://image.jpg");

        eventDao.create(a1);
        eventDao.create(a2);
    }

    @Test
    public void insert_inTimeline_persists() {
        StudyGroup studyGroup = new StudyGroup("Group1");
        em.persist(studyGroup);

        Timeline timeline = new Timeline("Alphabet", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31), studyGroup);
        em.persist(timeline);

        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");

        event.addTimeline(timeline);
        timeline.addEvent(event);

        eventDao.create(event);

        List<Event> rows = eventDao.findAll();
        assertThat(rows).containsExactlyInAnyOrder(event);
    }

    @Test
    public void findById_givenValidId_returnsEvent() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");

        eventDao.create(event);

        Optional<Event> foundEvent = eventDao.findById(event.getId());
        assertThat(foundEvent).hasValue(event);
    }

    @Test
    public void findById_givenInvalidId_returnNull() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");

        eventDao.create(event);

        Optional<Event> foundEvent = eventDao.findById(event.getId() + 999);
        assertThat(foundEvent).isEmpty();
    }

    @Test
    public void findById_givenNullId_throws() {
        assertThatExceptionOfType(DataAccessException.class)
            .isThrownBy(() -> eventDao.findById(null));
    }

    @Test
    public void findByName_givenValidName_returnsEvent() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");

        eventDao.create(event);

        Optional<Event> foundEvent = eventDao.findByName("A");
        assertThat(foundEvent).hasValue(event);
    }

    @Test
    public void findByName_givenInvalidName_returnNull() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");

        eventDao.create(event);

        Optional<Event> foundEvent = eventDao.findByName("BBB");
        assertThat(foundEvent).isEmpty();
    }

    @Test
    public void findByName_givenNullName_throws() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> eventDao.findByName(null));
    }


    @Test
    public void delete_singleValidEvent_isRemoved() {
        Event event = new Event("Event", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        eventDao.create(event);
        assertEquals(1, eventDao.findAll().size());

        eventDao.delete(event);
        assertEquals(0, eventDao.findAll().size());
    }

    @Test
    public void delete_multipleValidEvents_areRemoved() {
        Event a = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        Event b = new Event("B", LocalDate.of(2021, 8, 22), "Slovak republic", "Some other day of the week", "https://image.gif");
        Event c = new Event("C", LocalDate.of(2023, 9, 30), "England", "None provided", null);
        Event d = new Event("D", LocalDate.of(2023, 9, 30), "England", "None provided", null);
        Event e = new Event("E", LocalDate.of(2023, 9, 30), "England", "None provided", null);

        var events = new ArrayList<Event>();
        events.add(a);
        events.add(b);
        events.add(c);
        events.add(d);
        events.add(e);

        for (Event event: events) {
            eventDao.create(event);
        }

        eventDao.delete(events.get(1));
        eventDao.delete(events.get(3));

        assertThat(eventDao.findAll()).containsExactlyInAnyOrder(events.get(0), events.get(2), events.get(4));
    }

    @Test
    public void delete_nonExisting_throws() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        eventDao.delete(event);
    }

    @Test
    public void update_validEventWithNonUniqueField_updatedCorrectly() {
        Event event = new Event("A", LocalDate.of(2020, 4, 15), "Czech republic", "some da of the year", "https://image.jpg");
        eventDao.create(event);

        Optional<Event> foundEvent = eventDao.findById(event.getId());
        assertThat(foundEvent).hasValue(event);

        event.setLocation("Slovak republic");
        event.setImageIdentifier(null);

        eventDao.create(event);
        Optional<Event> updatedEvent = eventDao.findById(event.getId());
        assertThat(updatedEvent).hasValue(event);
    }
}