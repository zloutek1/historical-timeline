package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CommentDao;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Ondřej Machala
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Override
    public void create(@NonNull Comment comment) {
        try {
            commentDao.create(comment);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to create comment " + comment, ex);
        }
    }

    @Override
    public void update(@NonNull Comment comment) {
        try {
            commentDao.update(comment);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to update comment " + comment, ex);
        }
    }

    @Override
    public void delete(@NonNull Comment comment) {
        try {
            commentDao.delete(comment);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to delete comment " + comment, ex);
        }
    }

    @Override
    public Optional<Comment> findById(@NonNull Long id) {
        try {
            return commentDao.findById(id);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to retrieve comment with id " + id, ex);
        }
    }

    @Override
    public List<Comment> findAll() {
        try {
            return commentDao.findAll();
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to retrieve all comments", ex);
        }
    }
}
