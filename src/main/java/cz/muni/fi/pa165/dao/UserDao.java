package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

public interface UserDao {
    User findByEmail(String email);
}
