package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface UserDao extends CrudDao<User, Long> {
    /**
     * Searches for users with given email
     * @param email email of the user
     * @return user entity if exists, empty value otherwise
     */
    Optional<User> findByEmail(String email);
}
