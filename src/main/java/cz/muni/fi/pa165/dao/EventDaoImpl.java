package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Event;
import lombok.NonNull;

import java.util.Optional;

/**
 * DAO class for event entity.
 *
 * @author Eva Krajíková
 */

public class EventDaoImpl extends CrudDaoImpl<Event, Long> implements EventDao {
    protected EventDaoImpl() {
        super(Event.class);
    }

    @Override
    public Optional<Event> findByName(@NonNull String name) {
        return Optional.ofNullable(entityManager.createQuery("SELECT e FROM Event e WHERE e.name = :name", Event.class).setParameter("name", name).getSingleResult());
    }


}
