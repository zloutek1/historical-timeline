package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author David Sevcik
 */
@Service
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long registerUser(UserCreateDTO user, String unencryptedPassword) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.registerUser(mappedUser, unencryptedPassword);
    }

    @Override
    public void setUserRole(UserDTO user, UserRole role) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.setUserRole(mappedUser, role);
    }

    @Override
    public UserRole getUserRole(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.getUserRole(mappedUser);
    }

    @Override
    public Boolean authenticate(UserAuthenticateDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.authenticate(mappedUser, user.getPassword());
    }

    @Override
    public void registerToStudyGroup(UserDTO user, Long studyGroupID) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.registerToStudyGroup(mappedUser, studyGroupID);
    }

    @Override
    public void unregisterFromStudyGroup(UserDTO user, Long studyGroupID) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.unregisterFromStudyGroup(mappedUser, studyGroupID);
    }

    @Override
    public List<StudyGroupDTO> getUsersStudyGroups(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.getUsersStudyGroups(mappedUser)
                .stream()
                .map(studygroup -> beanMappingService.mapTo(studygroup, StudyGroupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(user -> beanMappingService.mapTo(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserByID(Long id) {
        Optional<User> user = userService.getUserById(id);
        UserDTO mappedUser = beanMappingService.mapTo(user.get(), UserDTO.class);
        return Optional.of(mappedUser);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = userService.getUserByEmail(email);
        UserDTO mappedUser = beanMappingService.mapTo(user.get(), UserDTO.class);
        return Optional.of(mappedUser);
    }
}
