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

    public void create(@NonNull T entity) {
        entityManager.persist(entity);
    }

    public void update(@NonNull T entity) {
        entityManager.merge(entity);
    }

    public void delete(@NonNull T entity) {
        entityManager.remove(entity);
    }

}
