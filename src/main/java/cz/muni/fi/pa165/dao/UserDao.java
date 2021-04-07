package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

import java.util.Optional;

/**
 * Dao interface for timeline entity
 *
 * @author Tomáš Ljutenko
 */
public interface UserDao {
    Optional<User> findByEmail(String email);
}
