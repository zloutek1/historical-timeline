package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.dao.UserDaoImpl;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

/**
 * @author David Sevcik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void register_givenNullUser_throws() {
        assertThrows(NullPointerException.class, () -> userService.registerUser(null, "some_password"));
    }

    @Test
    public void authenticate_givenNullUser_throws() {
        assertThrows(NullPointerException.class, () -> userService.authenticate(null, null));
    }

    @Test
    public void authenticate_givenProperPassword_returnTrue() {
        User user = new User("foo@bar.com",
                "Jan",
                "Novak",
                "$argon2id$v=19$m=4096,t=3,p=1$aQyc9w0FI4gtY8+iGWOA2g$hAWvXNwkMeCHESg7kTyt8hratocHX55GRHW5v1PTQgQ",
                UserRole.STUDENT);
        assertTrue(userService.authenticate(user, "some_password"));
    }

    @Test
    public void authenticate_givenWrongpassword_returnFalse() {
        User user = new User("foo@bar.com",
                "Jan",
                "Novak",
                "$argon2id$v=19$m=4096,t=3,p=1$aQyc9w0FI4gtY8+iGWOA2g$hAWvXNwkMeCHESg7kTyt8hratocHX55GRHW5v1PTQgQ",
                UserRole.STUDENT);
        assertFalse(userService.authenticate(user, "wrong_password"));
    }

}
