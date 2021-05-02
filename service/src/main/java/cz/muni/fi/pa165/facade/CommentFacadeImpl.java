package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.CommentUpdateDTO;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CommentService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Ondřej Machala
 */
@Service
@Transactional
public class CommentFacadeImpl implements CommentFacade {

    @Inject
    private CommentService commentService;

    @Inject
    private UserService userService;

    @Inject
    private TimelineService timelineService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createComment(@NonNull CommentCreateDTO commentCreate) {
        User user = userService.findUserByID(commentCreate.getUserId())
                .orElseThrow(() ->
                        new ServiceException("User with id " + commentCreate.getUserId() + " does not exist"));
        Timeline timeline = timelineService.findById(commentCreate.getTimelineId())
                .orElseThrow(() ->
                        new ServiceException("Timeline with id " + commentCreate.getTimelineId() + " does not exist"));
        Comment comment = new Comment(commentCreate.getText(), LocalDateTime.now());
        comment.setAuthor(user);
        comment.setTimeline(timeline);
        commentService.create(comment);
        return comment.getId();
    }

    @Override
    public void updateComment(@NonNull CommentUpdateDTO commentUpdate) {
        Comment comment= commentService.findById(commentUpdate.getId())
                .orElseThrow(() -> new ServiceException("Comment with id " + commentUpdate.getId() + " does not exist"));
        comment.setText(commentUpdate.getText());
    }

    @Override
    public void deleteComment(@NonNull Long id) {
        Comment comment = commentService.findById(id)
                .orElseThrow(() -> new ServiceException("Comment with id " + id + " does not exist"));
        commentService.delete(comment);
    }

    @Override
    public Optional<CommentDTO> findById(@NonNull Long id) {
        var comment = commentService.findById(id);
        if (comment.isEmpty()) return Optional.empty();
        var commentDto = beanMappingService.mapTo(comment.get(), CommentDTO.class);
        return Optional.of(commentDto);
    }
}
