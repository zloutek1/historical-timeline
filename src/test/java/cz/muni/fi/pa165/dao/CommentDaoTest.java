package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserRole;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.testng.Assert.*;

/**
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

    private User u1;
    private User u2;
    private StudyGroup a1;
    private Timeline t1;
    private Comment c1;

    @BeforeMethod
    public void setup() {
        u1 = new User("login@email.com", "Jan", "Novak", "nfjkdhgf5g45fdh", UserRole.STUDENT);
        u2 = new User("login2@email2.com", "Jiri", "Dvorak", "ljgfdkjgdfkth b", UserRole.TEACHER);
        em.persist(u1);
        em.persist(u2);

        a1 = new StudyGroup("A1");
        em.persist(a1);

        t1 = new Timeline("WW2", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 9, 2), a1);
        em.persist(t1);

        c1 = new Comment("Lorem ipsum", LocalDateTime.now());
        c1.setAuthor(u1);
        c1.setTimeline(t1);

        em.persist(c1);
    }

    @Test
    private void findById_givenIdOfExistingComment_returnsComment() {
        var existingComment = commentDao.findById(c1.getId());
        assertTrue(existingComment.isPresent());
    }

    @Test
    private void findById_givenNonexistingComment_returnsNull() {
        var existingComment = commentDao.findById(1000000L);
        assertFalse(existingComment.isPresent());
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

    @Test
    private void create_givenCommentAndChangeIt_itIsPersisted() {
        var comment = new Comment("This is just a test", LocalDateTime.now());
        comment.setAuthor(u2);
        comment.setTimeline(t1);
        commentDao.create(comment);

        var persistentComment = commentDao.findById(comment.getId());
        assertEquals(comment.getAuthor(), persistentComment.get().getAuthor());
        assertEquals(comment.getText(), persistentComment.get().getText());
        assertEquals(comment.getTime(), persistentComment.get().getTime());
    }

    @Test
    private void delete_givenCommentChange_persists() {
        commentDao.delete(c1);
        var deletedComment = commentDao.findById(c1.getId());
        assertFalse(deletedComment.isPresent());
    }

    @Test
    private void deleteById_givenCommentChange_persists() {
        commentDao.deleteById(c1.getId());
        var deletedComment = commentDao.findById(c1.getId());
        assertFalse(deletedComment.isPresent());
    }

    @Test(expectedExceptions = {DataAccessException.class})
    private void update_onNullThrows() {
        commentDao.update(null);
    }

    @Test
    private void update_givenCommentChange_persists() {
        var comment = commentDao.findById(c1.getId());
        var newText = "This is new text";
        comment.get().setText(newText);
        commentDao.update(comment.get());

        var commentAfterCommitedChange = commentDao.findById(c1.getId());
        assertEquals(commentAfterCommitedChange.get().getText(), newText);
    }
    @Test(expectedExceptions = {DataAccessException.class})
    private void update_deletedComment_throw() {
        commentDao.delete(c1);
        c1.setText("This should fail");
        commentDao.update(c1);
    }

    @Test
    private void findAll_whenOneComment_persists(){
        var comments = commentDao.findAll();
        assertEquals(comments.size(), 1);
    }

    @Test
    private void findAll_whenNoComment_persists(){
        commentDao.delete(c1);
        var comments = commentDao.findAll();
        assertEquals(comments.size(), 0);
    }

    @Test
    private void findByTimeline_whenNoCommentIsInTimeline_returnsEmptyList(){
        commentDao.delete(c1);
        var comments = commentDao.findByTimeline(t1);
        assertEquals(comments.size(), 0);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    private void findByTimeline_onNull_throws(){
        var comments = commentDao.findByTimeline(null);
    }

    @Test
    private void findByTimeline_onMultipleCommentsInTimeline_returnsListOfComments(){
        var comment = new Comment("This is just a test", LocalDateTime.now());
        comment.setAuthor(u2);
        comment.setTimeline(t1);
        commentDao.create(comment);

        var comments = commentDao.findByTimeline(t1);
        assertEquals(comments.size(), 2);
    }

}
