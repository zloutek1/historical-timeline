package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Timeline;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tomáš Ljutenko
 */
@Repository
public class TimelineDaoImpl extends CrudDaoImpl<Timeline, Long> implements TimelineDao {

    protected TimelineDaoImpl() { super(Timeline.class); }

    @Override
    public Optional<Timeline> findByName(@NonNull String name) {
        return entityManager.createQuery("select t from Timeline t where t.name = :name", Timeline.class)
                            .setParameter("name", name)
                            .getResultStream()
                            .findFirst();
    }
}
