package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.StudyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.testng.Assert.*;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class StudyGroupDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudyGroupDao studyGroupDao;

    @BeforeMethod
    public void setup() {
        StudyGroup a1 = new StudyGroup("A1");
        StudyGroup a2 = new StudyGroup("A2");
        StudyGroup b1 = new StudyGroup("B1");
        StudyGroup b2 = new StudyGroup("B2");
        em.persist(a1);
        em.persist(a2);
        em.persist(b1);
        em.persist(b2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void create_givenStudyGroupWithNullName_throw() {
        StudyGroup studyGroup = new StudyGroup(null);
        studyGroupDao.create(studyGroup);
    }

    @Test
    private void create_givenValidStudyGroup_itIsPersisted() {
        StudyGroup newStudyGroup = new StudyGroup("C1");
        studyGroupDao.create(newStudyGroup);
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(newStudyGroup.getId());
        assertTrue(studyGroup.isPresent());
        assertEquals(studyGroup.get().getName(), newStudyGroup.getName());
    }


    @Test
    private void findByName_givenExistingName_returnStudyGroup() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName("A1");
        assertTrue(studyGroup.isPresent());
        assertEquals(studyGroup.get().getName(),"A1");
    }

    @Test
    private void findByName_givenNonExistingName_returnNull() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName("NonexistentName");
        assertFalse(studyGroup.isPresent());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    private void findByName_givenNullName_throw() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName(null);
    }
}
