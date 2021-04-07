package cz.muni.fi.pa165.dao;
import cz.muni.fi.pa165.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author David Sevcik
 */
@Repository
public class UserDaoImpl extends CrudDaoImpl<User, Long> implements UserDao {
    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByEmail(@NonNull String email) {
       return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
          .setParameter("email", email)
          .getResultStream()
          .findFirst();
    }
}
