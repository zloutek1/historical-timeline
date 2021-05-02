package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.CommentUpdateDTO;

import java.util.Optional;

/**
 * Facade for working with comments
 *
 * @author Ondřej Machala
 */
public interface CommentFacade {
    /**
     * Stores a comment
     * @param commentCreate information regarding comment creation
     * @return id of new comment
     */
    Long createComment(CommentCreateDTO commentCreate);

    /**
     * Updates a comment
     * @param commentUpdate information regarding comment update
     */
    void updateComment(CommentUpdateDTO commentUpdate);

    /**
     * Deletes a comment
     * @param id of comment to delete
     */
    void deleteComment(Long id);

    /**
     * Fetches comment by it's id
     * @param id of comment
     * @return comment if stored, else empty value
     */
    Optional<CommentDTO> findById(Long id);
}
