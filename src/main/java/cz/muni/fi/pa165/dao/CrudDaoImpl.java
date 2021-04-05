package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.DbEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CrudDaoImpl<T extends DbEntity<U>, U> implements CrudDao<T,U> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected CrudDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findById(U id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");
        return entityManager.find(clazz, id);
    }

    public void create(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        if (entity.getId() != null) throw new IllegalArgumentException("Entity has id set");
        entityManager.persist(entity);
    }

    public void update(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        entityManager.remove(entity);
    }

}
