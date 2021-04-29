package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;

import java.util.Optional;

/**
 * @author Ondřej Machala
 */
public interface CommentFacade {
    Long createComment(CommentCreateDTO comment);
    void deleteComment(Long id);

    Optional<CommentDTO> findById(Long id);
}
