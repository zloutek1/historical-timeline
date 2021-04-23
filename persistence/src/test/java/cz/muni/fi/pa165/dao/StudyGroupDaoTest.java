package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.StudyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

/**
 * @author Ondřej Machala
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class StudyGroupDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudyGroupDao studyGroupDao;

    private StudyGroup a1;
    private StudyGroup a2;
    private StudyGroup b1;
    private StudyGroup b2;

    @BeforeMethod
    public void setup() {
        a1 = new StudyGroup("A1");
        a2 = new StudyGroup("A2");
        b1 = new StudyGroup("B1");
        b2 = new StudyGroup("B2");
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
    public void delete_givenExistingStudyGroup_removeIt() {
        studyGroupDao.delete(b2);
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(b2.getId());
        assertFalse(studyGroup.isPresent());
    }

    @Test
    public void update_givenUpdateToExistingStudyGroup_itIsUpdated() {
        b1.setName("E4");
        studyGroupDao.update(b1);
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(b1.getId());
        assertTrue(studyGroup.isPresent());
        assertEquals(studyGroup.get().getName(), "E4");
    }

    @Test
    private void findAll_returnsAllStudyGroups() {
        List<StudyGroup> all = studyGroupDao.findAll();
        assertEquals(all.size(), 4);
    }

    @Test(expectedExceptions = DataAccessException.class)
    private void findById_givenNullId_throw() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(null);
    }

    @Test
    private void findById_givenExistingId_returnStudyGroup() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(a1.getId());
        assertTrue(studyGroup.isPresent());
        assertEquals(studyGroup.get().getName(), a1.getName());
    }

    @Test
    private void findById_givenNonExistingId_returnEmpty() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findById(222222l);
        assertFalse(studyGroup.isPresent());
    }

    @Test
    private void findByName_givenExistingName_returnStudyGroup() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName(a1.getName());
        assertTrue(studyGroup.isPresent());
        assertEquals(studyGroup.get().getName(),a1.getName());
    }

    @Test
    private void findByName_givenNonExistingName_returnEmpty() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName("NonexistentName");
        assertFalse(studyGroup.isPresent());
    }

    @Test(expectedExceptions = DataAccessException.class)
    private void findByName_givenNullName_throw() {
        Optional<StudyGroup> studyGroup = studyGroupDao.findByName(null);
    }
}
