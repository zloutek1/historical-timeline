package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author David Sevcik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserService userService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    private AutoCloseable closeable;

    private User user;
    private UserDTO userDTO;
    private StudyGroup studyGroup;
    private final List<StudyGroup> studyGroups = new ArrayList<>();

    private final String email = "foo@bar.com";
    private final String firstName = "Jan" ;
    private final String lastName = "Novak";
    private final String passwordHash = "Some_hash";
    private final String unencryptedPassword = "password";
    private final UserRole userRole = UserRole.STUDENT;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        user = new User(email, firstName, lastName, passwordHash, userRole);
        user.setId(1L);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail(email);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPasswordHash(passwordHash);
        userDTO.setRole(userRole);


        studyGroup = new StudyGroup("A1");
        studyGroup.setId(1L);

        studyGroups.add(studyGroup);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void registerUser_givenValidDTO_callsRegisterUser() {
        var createDTO = new UserCreateDTO();
        createDTO.setEmail(email);
        createDTO.setFirstName(firstName);
        createDTO.setLastName(lastName);
        createDTO.setPassword(unencryptedPassword);

        when(beanMappingService.mapTo(createDTO, User.class)).thenReturn(user);

        userFacade.registerUser(createDTO);

        verify(userService).registerUser(user, unencryptedPassword);
    }

    @Test
    public void authenticate_givenValidPassword_callsAuthenticateAndReturnsTrue() {
        var authenticateDTO = new UserAuthenticateDTO();
        authenticateDTO.setEmail("email");
        authenticateDTO.setPassword(unencryptedPassword);

        when(userService.findUserByEmail("email")).thenReturn(Optional.of(user));
        when(userService.authenticate(user, unencryptedPassword)).thenReturn(true);

        var status = userFacade.authenticate(authenticateDTO);

        verify(userService).authenticate(user, unencryptedPassword);
        assertTrue(status);
    }

    @Test
    public void authenticate_givenInvalidPassword_callsAuthenticateAndReturnsFalse() {
        var authenticateDTO = new UserAuthenticateDTO();
        authenticateDTO.setEmail("email");
        authenticateDTO.setPassword(unencryptedPassword);

        when(userService.findUserByEmail("email")).thenReturn(Optional.of(user));
        when(userService.authenticate(user, unencryptedPassword)).thenReturn(false);

        var status = userFacade.authenticate(authenticateDTO);

        verify(userService).authenticate(user, unencryptedPassword);
        assertFalse(status);
    }

    @Test
    public void changePassword_givenValidPassword_callsChangePassword() {
        var userID = user.getId();
        var newPassword = "new password";

        userFacade.changeUserPassword(user.getId(), newPassword);

        verify(userService).changeUserPassword(user.getId(), newPassword);
    }

    @Test
    public void updateUser_givenValidDTO_callsUpdateUser() {
        var newName = "Ladislav";

        var updateDTO = new UserUpdateDTO();
        updateDTO.setFirstName(newName);
        user.setFirstName(newName);

        when(beanMappingService.mapTo(updateDTO, User.class)).thenReturn(user);

        userFacade.updateUser(user.getId(), updateDTO);

        verify(userService).updateUser(user.getId(), user);
    }

    @Test
    public void registerToStudyGroup_givenValidIDs_callsRegisterToStudyGroup() {
        userFacade.registerToStudyGroup(user.getId(), studyGroup.getId());
        verify(userService).registerToStudyGroup(user.getId(), studyGroup.getId());
    }

    @Test
    public void unregisterFromStudyGroup_givenValidIDs_callsUnregisterFromStudyGroup() {
        userFacade.unregisterFromStudyGroup(user.getId(), studyGroup.getId());
        verify(userService).unregisterFromStudyGroup(user.getId(), studyGroup.getId());
    }

    @Test
    public void findUsersStudyGroups_givenValidID_callsFindUsersStudyGroupAndReturnsList() {
        StudyGroupDTO studyGroupDTO = new StudyGroupDTO();
        studyGroupDTO.setName("A1");

        when(userService.findUserStudyGroups(user.getId())).thenReturn(studyGroups);
        when(beanMappingService.mapTo(studyGroup, StudyGroupDTO.class)).thenReturn(studyGroupDTO);

        var list = userFacade.findUserStudyGroups(user.getId());

        verify(userService).findUserStudyGroups(user.getId());
        assertEquals(list.size(), studyGroups.size());
        assertEquals(list.get(0).getName(), studyGroups.get(0).getName());

    }

    @Test
    public void findAllUsers_CallsFindAllUsersAndReturnList() {
        var userList = new ArrayList<User>();
        userList.add(user);

        when(userService.findAllUsers()).thenReturn(userList);
        when(beanMappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        var list = userFacade.findAllUsers();

        verify(userService).findAllUsers();
        assertEquals(list.size(), userList.size());
        assertEquals(list.get(0).getEmail(), userList.get(0).getEmail());
    }

    @Test
    public void findUserByID_givenValidID_CallsFindUserByIDAndReturnUser() {
        when(userService.findUserByID(user.getId())).thenReturn(Optional.of(user));
        when(beanMappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        var userDTObyID = userFacade.findUserByID(userDTO.getId());

        verify(userService).findUserByID(user.getId());
        assertEquals(userDTObyID.get(), userDTO);
    }

    @Test
    public void findUserByID_givenInvalidID_CallsFindUserByIDAndReturnEmptyOptional() {
        when(userService.findUserByID(user.getId())).thenReturn(Optional.empty());

        var userDTObyID = userFacade.findUserByID(userDTO.getId());

        verify(userService).findUserByID(user.getId());
        assertTrue(userDTObyID.isEmpty());
    }

    @Test
    public void findUserByEmail_givenValidEmail_CallsFindUserByEmailAndReturnUser() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(beanMappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        var userDTObyEmail = userFacade.findUserByEmail(userDTO.getEmail());

        verify(userService).findUserByEmail(user.getEmail());
        assertEquals(userDTObyEmail.get(), userDTO);
    }

    @Test
    public void findUserByEmail_givenInvalidEmail_CallsFindUserByEmailAndReturnEmptyOptional() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());

        var userDTObyEmail = userFacade.findUserByEmail(userDTO.getEmail());

        verify(userService).findUserByEmail(user.getEmail());
        assertTrue(userDTObyEmail.isEmpty());
    }

}
