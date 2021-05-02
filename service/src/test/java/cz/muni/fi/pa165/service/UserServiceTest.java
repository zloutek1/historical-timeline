package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;

import cz.muni.fi.pa165.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertThrows;

/**
 * @author David Sevcik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Mock
    private StudyGroupDao studyGroupDao;

    @Inject
    @InjectMocks
    private UserService userService;

    private AutoCloseable closeable;

    private User user1;
    private User user2;
    private User user3;
    private final List<User> users = new ArrayList<>();
    private StudyGroup studyGroup;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @BeforeMethod
    public void setEntities(){
        user1 = new User("foo@bar.com", "Jan", "Novak", "some_hash", UserRole.STUDENT);
        user1.setId(1L);
        user2 = new User("lkdfl@bar.cz", "Jiri", "Dvorak", "some_hash", UserRole.TEACHER);
        user2.setId(2L);
        user3 = new User("bar@foo.net", "Josef", "Horky", "some_hash", UserRole.ADMINISTRATOR);
        user3.setId(3L);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        studyGroup = new StudyGroup("A1");
        studyGroup.setId(1L);

        user1.addStudyGroup(studyGroup);

    }

    @Test
    public void register_givenNullUser_throws() {
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(null, "some_password"));
    }

    @Test
    public void register_givenUser_callsDaoCreate() {
        String password = "password";
        when(userDao.findByEmail(user1.getEmail())).thenReturn(ofNullable(user1));

        userService.registerUser(user1, password);

        verify(userDao).create(user1);
    }

    @Test
    public void register_givenFailingDatabase_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(userDao).create(any(User.class));
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> userService.registerUser(user1, "Some password"));
    }

    @Test
    public void authenticate_givenNullUser_throws() {
        assertThrows(IllegalArgumentException.class, () -> userService.authenticate(null, null));
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
    public void authenticate_givenInvalidPassword_returnFalse() {
        User user = new User("foo@bar.com",
                "Jan",
                "Novak",
                "$argon2id$v=19$m=4096,t=3,p=1$aQyc9w0FI4gtY8+iGWOA2g$hAWvXNwkMeCHESg7kTyt8hratocHX55GRHW5v1PTQgQ",
                UserRole.STUDENT);
        assertFalse(userService.authenticate(user, "wrong_password"));
    }

    @Test
    public void update_givenNull_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.updateUser(user1.getId(), null));
    }

    @Test
    public void registerToStudyGroup_givenProperID_callsUpdate() {
        when(studyGroupDao.findById(studyGroup.getId())).thenReturn(ofNullable(studyGroup));
        when(userDao.findById(user2.getId())).thenReturn(ofNullable(user2));

        userService.registerToStudyGroup(user2.getId(), studyGroup.getId());

        verify(userDao).update(user2);
    }

    @Test
    public void registerToStudyGroup_givenInvalidID_throws() {
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> userService.registerToStudyGroup(user1.getId(), studyGroup.getId()));
    }

    @Test
    public void registerToStudyGroup_givenNull_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.registerToStudyGroup(user1.getId(), null));
    }

    @Test
    public void unregisterFromStudyGroup_givenProperID_callsUpdate() {
        when(studyGroupDao.findById(studyGroup.getId())).thenReturn(ofNullable(studyGroup));
        when(userDao.findById(user1.getId())).thenReturn(ofNullable(user1));

        userService.unregisterFromStudyGroup(user1.getId(), studyGroup.getId());

        verify(userDao).update(user1);
    }

    @Test
    public void unregisterFromStudyGroup_givenInvalidParams_throws() {
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> userService.unregisterFromStudyGroup(user1.getId(), studyGroup.getId()));
    }

    @Test
    public void unregisterFromStudyGroup_givenNull_throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.unregisterFromStudyGroup(user1.getId(), null));
    }

    @Test
    public void findUserStudyGroups_givenProperID_returnsStudyGroup() {
        when(userDao.findById(user1.getId())).thenReturn(ofNullable(user1));
        var list = userService.findUserStudyGroups(user1.getId());
        assertEquals(list.get(0), studyGroup);
    }

    @Test
    public void findAllUsers_returnsUsers() {
        when(userDao.findAll()).thenReturn(users);
        var userList = userService.findAllUsers();
        assertEquals(users, userList);
    }

    @Test
    public void findAllUsers_returnsEmptyList() {
        var userList = userService.findAllUsers();
        assertTrue(userList.isEmpty());
    }

    @Test
    public void findUserByID_givenProperID_returnsUser() {
        when(userDao.findById(user1.getId())).thenReturn(ofNullable(user1));
        var user = userService.findUserByID(user1.getId());
        assertEquals(user.get(), user1);
    }

    @Test
    public void findUserByID_givenInvalidID_returnsOptionalEmpty() {
        assertTrue(userService.findUserByID(user2.getId()).isEmpty());
    }

    @Test
    public void findUserByID_givenNull_Throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.findUserByID(null));
    }

    @Test
    public void findUserByEmail_givenProperEmail_returnsUser() {
        when(userDao.findByEmail(user1.getEmail())).thenReturn(ofNullable(user1));
        var user = userService.findUserByEmail(user1.getEmail());
        assertEquals(user.get(), user1);
    }

    @Test
    public void findUserByID_givenInvalidEmail_returnsOptionalEmpty() {
        assertTrue(userService.findUserByEmail(user2.getEmail()).isEmpty());
    }

    @Test
    public void findUserByEmail_givenNull_Throws() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.findUserByEmail(null));
    }

}
