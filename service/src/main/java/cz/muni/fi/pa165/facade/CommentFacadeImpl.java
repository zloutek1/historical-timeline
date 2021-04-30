package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CommentService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createComment(CommentCreateDTO comment) {
        Optional<User> user = userService.findUserByID(comment.getUserId());
        if (user.isEmpty())
            throw new ServiceException("User with id " + comment.getUserId() + " does not exist");
        Optional<Timeline> timeline = timelineService.getById(comment.getTimelineId());
        if (timeline.isEmpty())
            throw new ServiceException("Timeline with id " + comment.getTimelineId() + " does not exist");
        Comment newComment = new Comment(comment.getText(), LocalDateTime.now());
        newComment.setAuthor(user.get());
        newComment.setTimeline(timeline.get());
        commentService.create(newComment);
        return newComment.getId();
    }

    @Override
    public void deleteComment(Long id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isEmpty())
            throw new ServiceException("Comment with id " + id + " does not exist");
        commentService.delete(comment.get());
    }

    @Override
    public Optional<CommentDTO> findById(Long id) {
        var comment = commentService.findById(id);
        if (comment.isEmpty()) return Optional.empty();
        var commentDto = beanMappingService.mapTo(comment.get(), CommentDTO.class);
        return Optional.of(commentDto);
    }
}
