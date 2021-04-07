package cz.muni.fi.pa165.dao;
import cz.muni.fi.pa165.entity.User;

import javax.persistence.NoResultException;

/**
 * DAO class for user entity.
 *
 * @author David Sevcik
 */
public class UserDaoImpl extends CrudDaoImpl<User, Long> implements UserDao {
    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByFullName(String firstname, String lastname) {
        if ((firstname == null) || (lastname == null)) throw new IllegalArgumentException("First and Last Name cannot be null");
        try {
           return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.firstName LIKE :firstname AND u.lastName LIKE :lastname")
                      .setParameter("firstname", firstname)
                      .setParameter("lastname", lastname)
                      .getSingleResult();
        } catch (NoResultException ex) {
           return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("email cannot be null");
        try {
            return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.email LIKE :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
