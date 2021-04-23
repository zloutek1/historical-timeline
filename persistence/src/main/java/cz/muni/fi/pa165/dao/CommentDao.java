package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Timeline;

import java.util.List;

/**
 * @author Ondřej Machala
 */
public interface CommentDao extends CrudDao<Comment, Long> {
    /**
     * Finds comments belonging under the given timeline
     *
     * @param timeline timeline under which the comments belong
     * @return list of all the comments under the timeline
     */
    List<Comment> findByTimeline(Timeline timeline);
}
