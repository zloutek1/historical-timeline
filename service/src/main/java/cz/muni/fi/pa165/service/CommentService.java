package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Comment;
import java.util.List;
import java.util.Optional;

/**
 * @author Ondřej Machala
 */
public interface CommentService {
    void create(Comment comment);
    void update(Comment comment);
    void delete(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> getAll();
}
