package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Timeline;

import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
public interface TimelineDao extends CrudDao<Timeline, Long> {

    /**
     * Finds a timeline with a given name
     *
     * @param name name of the timeline
     * @return timeline entity if exists, empty value otherwise
     */
    Optional<Timeline> findByName(String name);

}
