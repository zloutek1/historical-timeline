package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Comment;
import java.util.List;
import java.util.Optional;

/**
 * A service for working with comment entities
 *
 * @author Ondřej Machala
 */
public interface CommentService {
    /**
     * Stores a comment entity
     * @param comment to be stored
     */
    void create(Comment comment);

    /**
     * Updates a comment entity
     * @param comment to be updated
     */
    void update(Comment comment);

    /**
     * Deletes a comment entity
     * @param comment to be deleted
     */
    void delete(Comment comment);

    /**
     * Fetches comment by id
     * @param id of the comment to fetch
     * @return comment if found, else empty value
     */
    Optional<Comment> findById(Long id);

    /**
     * Fetches all comments
     * @return all stored comments
     */
    List<Comment> findAll();
}
