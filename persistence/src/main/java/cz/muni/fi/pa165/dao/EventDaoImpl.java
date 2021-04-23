package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Eva Krajíková
 */

@Repository
public class EventDaoImpl extends CrudDaoImpl<Event, Long> implements EventDao {
    protected EventDaoImpl() {
        super(Event.class);
    }

    @Override
    public Optional<Event> findByName(@NonNull String name) {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.name = :name", Event.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }


}
