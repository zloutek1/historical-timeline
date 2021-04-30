package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.CommentDao;
import cz.muni.fi.pa165.entity.Comment;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class CommentServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CommentDao commentDao;

    @Autowired
    @InjectMocks
    private CommentService commentService;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void finish() throws Exception {
        closeable.close();
    }

    private Comment commentValid = new Comment("Pointless comment", LocalDateTime.now());
    private Comment commentInvalid = new Comment(null, LocalDateTime.now());

    @Test
    public void create_givenValidComment_creates() {
        commentService.create(commentValid);
        verify(commentDao).create(commentValid);
    }

    @Test
    public void create_givenInvalidComment_throws() {
        doThrow(ConstraintViolationException.class).when(commentDao).create(commentInvalid);
        assertThrows(Exception.class, () -> commentService.create(commentInvalid));
    }

    @Test
    public void create_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> commentService.create(null));
    }

    @Test
    public void delete_givenComment_deletes() {
        commentService.delete(commentValid);
        verify(commentDao).delete(commentValid);
    }

    @Test
    public void delete_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> commentService.delete(null));
    }

    @Test
    public void findById_givenExistingId_returnsComment() {
        when(commentDao.findById(3l)).thenReturn(Optional.of(commentValid));
        var comment = commentService.findById(3l);
        assertThat(comment).contains(commentValid);
    }

    @Test
    public void findById_givenNonExistingId_returnsEmpty() {
        when(commentDao.findById(3l)).thenReturn(Optional.empty());
        var comment = commentService.findById(3l);
        assertThat(comment).isEmpty();
    }

    @Test
    public void findById_givenNullId_throws() {
        assertThrows(IllegalArgumentException.class, () -> commentService.findById(null));
    }

}
