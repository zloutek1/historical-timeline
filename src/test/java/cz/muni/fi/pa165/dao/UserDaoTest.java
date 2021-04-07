package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

/**
 * @author Ondřej Machala
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    private User alice;
    private User bob;
    private User cecilia;

    @BeforeMethod
    public void setup() {
        alice = new User("alice@gmail.com", "Alice", "Palice", "passwordHash", UserRole.ADMINISTRATOR );
        bob = new User("bob@gmail.com", "Bob", "Blob", "passwordHash", UserRole.TEACHER );
        cecilia = new User("cecilia@gmail.com", "Cecilia", "Idunno", "passwordHash", UserRole.STUDENT );
        em.persist(alice);
        em.persist(bob);
        em.persist(cecilia);
    }

    @Test(priority = 1)
    public void create_givenValidUser_itIsPersisted() {
        User john = new User("user@gmail.com", "John", "Doe", "passwordHash", UserRole.STUDENT );
        userDao.create(john);
        Optional<User> user = userDao.findById(john.getId());
        assertTrue(user.isPresent());
        assertEquals(user.get(), john);
    }

    @Test(priority = 1,expectedExceptions = ConstraintViolationException.class)
    public void create_givenNullEmail_Throws() {
        User john = new User(null, "John", "Doe", "passwordHash", UserRole.STUDENT );
        userDao.create(john);
    }

    @Test(priority = 1, expectedExceptions = ConstraintViolationException.class)
    public void create_givenNullNames_Throws() {
        User john = new User("user123@gmail.com", null, null, "passwordHash", UserRole.STUDENT );
        userDao.create(john);
    }

    @Test(priority = 1, expectedExceptions = DataAccessException.class)
    public void create_givenDuplicateEmail_Throws() {
        User user = new User("peter@gmail.com", "Peter", "Happy", "passwordHash", UserRole.STUDENT );
        userDao.create(user);
        User duplicateUser = new User("peter@gmail.com", "Peter", "Unhappy", "passwordHash", UserRole.STUDENT );
        userDao.create(duplicateUser);;
    }

    @Test(priority = 2)
    public void delete_givenExistingUser_removeHim() {
        userDao.delete(alice);
        Optional<User> user = userDao.findById(alice.getId());
        assertFalse(user.isPresent());
    }

    @Test(priority = 2)
    public void deleteById_givenExistingIdOfUser_removeHim() {
        userDao.deleteById(bob.getId());
        Optional<User> user = userDao.findById(bob.getId());
        assertFalse(user.isPresent());
    }

    @Test(priority = 1)
    public void update_givenUpdateToExistingUser_heIsUpdated() {
        cecilia.setRole(UserRole.ADMINISTRATOR);
        userDao.update(cecilia);
        Optional<User> user = userDao.findById(cecilia.getId());
        assertTrue(user.isPresent());
        assertEquals(user.get().getRole(), UserRole.ADMINISTRATOR);
    }

    @Test
    public void findAll_returnsAll() {
        List<User> all = userDao.findAll();
        assertEquals(all.size(), 3);
    }

    @Test
    public void findById_givenIdOfExistingUser_returnUser() {
        Optional<User> user = userDao.findById(cecilia.getId());
        assertTrue(user.isPresent());
    }

    @Test
    public void findByEmail_givenEmailOfExistingUser_returnUser() {
        Optional<User> user = userDao.findByEmail(cecilia.getEmail());
        assertTrue(user.isPresent());
    }

    @Test
    public void findByEmail_givenEmailOfNonExistingUser_returnEmpty() {
        Optional<User> user = userDao.findByEmail("blebla@gmail.blo");
        assertFalse(user.isPresent());
    }

}
