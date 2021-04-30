package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.CommentUpdateDTO;

import java.util.Optional;

/**
 * @author Ondřej Machala
 */
public interface CommentFacade {
    Long createComment(CommentCreateDTO comment);
    void updateComment(CommentUpdateDTO comment);
    void deleteComment(Long id);
    Optional<CommentDTO> findById(Long id);
}
