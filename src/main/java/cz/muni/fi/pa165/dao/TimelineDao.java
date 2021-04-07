package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Timeline;

import java.util.Optional;

public interface TimelineDao extends CrudDao<Timeline, Long> {

    Optional<Timeline> findByName(String name);

}
