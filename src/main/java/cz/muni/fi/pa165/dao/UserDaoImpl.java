package cz.muni.fi.pa165.dao;
import cz.muni.fi.pa165.entity.User;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

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
    public Optional<User> findByEmail(@NotNull String email) {
       return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
          .setParameter("email", email)
          .getResultStream()
          .findFirst();
    }
}
