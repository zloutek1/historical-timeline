package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.DbEntity;

public interface CrudDao<T extends DbEntity<U>, U> {
    void create(T entity);
    void delete(T entity);
    void update(T entity);
    T findById(U id);
}
