package cz.muni.fi.pa165.dao;

import java.util.List;
import java.util.Optional;

/**
 * A generic dao interface for CRUD operations
 *
 * @author Ondřej Machala, Tomáš Ljutenko
 *
 * @param <T> Type of the entity class
 * @param <U> Type of the primary key of the entity class
 */
public interface CrudDao<T, U> {
    /**
     * Persists the entity
     *
     * @param entity entity to be persisted
     */
    void create(T entity);

    /**
     * Updates the entity
     *
     * @param entity entity to be updated
     */
    void update(T entity);

    /**
     * Removes the entity
     *
     * @param entity entity to be removed
     */
    void delete(T entity);

    /**
     * Removes the entity
     *
     * @param id id of the entity to be removed
     */
    void deleteById(U id);

    /**
     * Fetches all the stored entities
     *
     * @return all the entities
     */
    List<T> findAll();

    /**
     * Finds entity with the given id
     *
     * @param id identifier of the entity to be found
     * @return the entity if it is stored, else empty value
     */
    Optional<T> findById(U id);
}
