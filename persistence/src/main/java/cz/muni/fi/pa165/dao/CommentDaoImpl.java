package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ondřej Machala
 */
@Repository
public class CommentDaoImpl extends CrudDaoImpl<Comment,Long> implements CommentDao {

    public CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findByTimeline(Timeline timeline) {
        if (timeline == null) throw new IllegalArgumentException("Timeline cannot be null");
        return entityManager
                .createQuery("SELECT c FROM Comment c WHERE c.timeline = :timelineId", Comment.class)
                .setParameter("timelineId", timeline)
                .getResultList();
    }
}
