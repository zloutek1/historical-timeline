package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class with CommentDao unit tests.
 *
 * @author David Sevcik
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CommentDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CommentDao commentDao;

    @BeforeMethod
    public void setup() {
        User u1 = new User("login@email.com", "Jan", "Novak", "nfjkdhgf5g45fdh", UserRole.STUDENT);
        User u2 = new User("login2@email2.com", "Jiri", "Dvorak", "ljgfdkjgdfkth b", UserRole.TEACHER);
        em.persist(u1);
        em.persist(u2);

        StudyGroup a1 = new StudyGroup("A1");

        Timeline t1 = new Timeline("testing timeline", LocalDate.now(), LocalDate.now(), a1);

        //Timeline t1 = new Timeline("WW2", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 9, 2), a1);

        em.persist(a1);
        em.persist(t1);

//        Timeline t1 = new Timeline();
//        t1.setName("WW2");
//        t1.setFrom(LocalDate.of(1939, 9, 1));
//        t1.setTo(LocalDate.of(1945, 9, 2));
//        t1.setStudyGroup(a1);

//        Timeline t1 = new Timeline(null, "WW2", LocalDate.of(1939, 9, 1), LocalDate.of(1945,9,2), a1);



//        var com1 = new Comment("Lorem ipsum", LocalDateTime.now());
//        com1.setAuthor(u1);
        //com1.setTimeline(t1);

//        em.persist(com1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void create_givenCommentWithNullTimeline_throw() {
        var comment = new Comment("This is jst a test", null);
        commentDao.create(comment);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void create_givenCommentWithNullText_throw() {
        var comment = new Comment(null, LocalDateTime.now());
        commentDao.create(comment);
    }


}
