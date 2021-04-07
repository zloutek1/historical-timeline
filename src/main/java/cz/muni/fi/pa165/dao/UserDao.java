package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

import java.util.Optional;

/**
 * Dao interface for timeline entity
 *
 * @author Tomáš Ljutenko
 */
public interface UserDao extends CrudDao<User, Long> {
    /**
     * If user with given email is found, returns optional object that contains it, otherwise it contains null
     * @param email email of the user
     * @return optional container object that either contains User object or contains null value (if there is no user with given email)
     */
    Optional<User> findByEmail(String email);
}
