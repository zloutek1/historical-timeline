package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;

import java.util.List;

/**
 * @author Ondřej Machala
 */
public interface CommentDao extends CrudDao<Comment, Long> {
    List<Comment> findByTimeline(Timeline timeline);
}
