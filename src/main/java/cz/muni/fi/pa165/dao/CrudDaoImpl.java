package cz.muni.fi.pa165.dao;

import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CrudDaoImpl<T, U> implements CrudDao<T, U> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected CrudDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(@NonNull T entity) {
        entityManager.persist(entity);
    }

    public void update(@NonNull T entity) {
        entityManager.merge(entity);
    }

    public void delete(@NonNull T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(U id) {
        Optional<T> entity = findById(id);
        entity.ifPresent(this::delete);
    }

    public List<T> findAll(){
        return entityManager.createQuery( "from " + clazz.getName(), clazz ).getResultList();
    }

    public Optional<T> findById(@NonNull U id) {
        return Optional.of(entityManager.find(clazz, id));
    }


}
