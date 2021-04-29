package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CommentDao;
import cz.muni.fi.pa165.entity.Comment;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
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
        commentDao.create(comment);
    }

    @Override
    public void update(@NonNull Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void delete(@NonNull Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public Optional<Comment> findById(@NonNull Long id) {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.findAll();
    }
}
