package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Event;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author Eva Krajíková
 */

public interface EventDao extends CrudDao<Event, Long> {
    Optional<Event> findByName(@NonNull String name);
}
