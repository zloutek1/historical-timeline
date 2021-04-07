package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;



/**
 * @author Eva Krajníková
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional

public class TimelineDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TimelineDao timelineDao;

    private Timeline t1;

    @BeforeMethod
    public void setUp(){
        StudyGroup s = new StudyGroup("S");
        em.persist(s);
        t1 = new Timeline("T",
                LocalDate.of(2000,1,2),
                LocalDate.of(2000,2,3),
                s);
    }

    @Test
    public void findAll_withEmptyDatabase_isEmpty(){
        assertEquals(timelineDao.findAll().size(), 0);
    }

    @Test
    public void create_withValidTimeline_persists(){
        timelineDao.create(t1);
        Optional<Timeline> t2 = timelineDao.findById(t1.getId());
        assertTrue(t2.isPresent());
        assertEquals(t1, t2.get());
    }

    @Test (expectedExceptions = ConstraintViolationException.class)
    public void create_withInvalidTimeline_throws(){
        Timeline t1 = new Timeline(null, null, null, null);
        timelineDao.create(t1);
    }

    @Test
    public void update_withValidTimeline_updates(){
        timelineDao.create(t1);
        t1.setName("T1");
        timelineDao.update(t1);
        Optional<Timeline> t2 = timelineDao.findById(t1.getId());
        assertTrue(t2.isPresent());
        assertEquals(t1, t2.get());
    }

    @Test
    public void delete_withKnownTimeline_deletes(){
        timelineDao.create(t1);
        timelineDao.delete(t1);
        Optional<Timeline> t2 = timelineDao.findById(t1.getId());
        assertTrue(t2.isEmpty());
    }

    @Test
    public void deleteById_withKnownTimeline_deletes(){
        timelineDao.create(t1);
        timelineDao.deleteById(t1.getId());
        Optional<Timeline> t2 = timelineDao.findById(t1.getId());
        assertTrue(t2.isEmpty());
    }

    @Test
    public void findByName_withKnownTimeline_find(){
        timelineDao.create(t1);
        Optional<Timeline> t2 = timelineDao.findByName(t1.getName());
        assertTrue(t2.isPresent());
        assertEquals(t1, t2.get());
    }

    @Test
    public void findByName_withUnknownTimeline_isEmpty(){
        Optional<Timeline> t2 = timelineDao.findByName(t1.getName());
        assertTrue(t2.isEmpty());
    }
    
}

