package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

public interface UserDao {
    User findByFullName(String firstname, String lastname);
    User findByEmail(String email);
}
