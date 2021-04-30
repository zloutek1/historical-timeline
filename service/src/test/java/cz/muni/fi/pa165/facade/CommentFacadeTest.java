package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.CommentUpdateDTO;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CommentService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class CommentFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @Mock
    private TimelineService timelineService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private CommentFacade commentFacade = new CommentFacadeImpl();

    private Comment commentEntity;
    private User userEntity;
    private Timeline timelineEntity;
    private CommentDTO commentDTO;
    private long id = 3l;
    private LocalDateTime now = LocalDateTime.now();

    private AutoCloseable closeable;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        commentEntity = new Comment("Comment", now);
        commentEntity.setId(id);

        userEntity = new User();
        userEntity.setId(2l);
        userEntity.setEmail("bla@ble.blu");

        timelineEntity = new Timeline();
        timelineEntity.setId(4l);
        timelineEntity.setName("T1");

        commentDTO = new CommentDTO();
        commentDTO.setText("Comment");
        commentDTO.setTime(now);
        commentDTO.setId(id);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void findById_givenValidId_returnDTO() {
        when(commentService.findById(id)).thenReturn(Optional.of(commentEntity));
        when(beanMappingService.mapTo(commentEntity,CommentDTO.class)).thenReturn(commentDTO);
        var comment = commentFacade.findById(id);
        assertThat(comment).contains(commentDTO);
    }

    @Test
    public void create_givenValidDTO_createEntity() {
        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setText("Comment");
        commentCreateDTO.setUserId(2l);
        commentCreateDTO.setTimelineId(4l);
        when(userService.findUserByID(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(timelineService.getById(timelineEntity.getId())).thenReturn(Optional.of(timelineEntity));
        commentFacade.createComment(commentCreateDTO);
        verify(commentService).create(any());
    }

    @Test
    public void update_givenExistingComment_updateEntity() {
        CommentUpdateDTO commentUpdateDTO = new CommentUpdateDTO();
        commentUpdateDTO.setId(commentEntity.getId());
        commentUpdateDTO.setText("Update");
        when(commentService.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        commentFacade.updateComment(commentUpdateDTO);
        verify(commentService).update(commentEntity);
        assertThat(commentEntity.getText()).isEqualTo("Update");
    }

    @Test
    public void delete_givenValidId_deleteEntity() {
        when(commentService.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        commentFacade.deleteComment(commentEntity.getId());
        verify(commentService).delete(commentEntity);
    }

}
