package cz.muni.fi.pa165.dao;

import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Ondřej Machala, Tomáš Ljutenko
 *
 * @param <T> Type of the entity class
 * @param <U> Type of the primary key of the entity class
 */
public class CrudDaoImpl<T, U> implements CrudDao<T, U> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected CrudDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void create(@NonNull T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(@NonNull T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(@NonNull T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(U id) {
        Optional<T> entity = findById(id);
        entity.ifPresent(this::delete);
    }

    @Override
    public List<T> findAll(){
        return entityManager.createQuery( "from " + clazz.getName(), clazz ).getResultList();
    }

    @Override
    public Optional<T> findById(@NonNull U id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

}
