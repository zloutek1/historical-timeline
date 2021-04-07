package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);
}
