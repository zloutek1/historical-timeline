package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Event;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author Eva Krajíková
 */

public interface EventDao extends CrudDao<Event, Long> {
    /**
     * Finds Event by name
     * @param name Event name
     * @return Empty if null, Event otherwise
     */
    Optional<Event> findByName(String name);
}
