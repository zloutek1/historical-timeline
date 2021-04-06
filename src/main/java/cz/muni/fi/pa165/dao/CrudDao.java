package cz.muni.fi.pa165.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, U> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteById(U id);

    List<T> findAll();
    Optional<T> findById(U id);
}
