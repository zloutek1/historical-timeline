package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author David Sevcik
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long registerUser(UserCreateDTO user, String unencryptedPassword) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.registerUser(mappedUser, unencryptedPassword);
    }

    @Override
    public Boolean authenticate(UserAuthenticateDTO user) {
        Optional<User> userEntity = userService.findUserByEmail(user.getEmail());
        return userService.authenticate(userEntity.get(), user.getPassword());
    }

    @Override
    public void changeUserPassword(Long userID, String unencryptedPassword) {
        userService.changeUserPassword(userID, unencryptedPassword);
    }

    @Override
    public void updateUser(Long userID, UserUpdateDTO userUpdate) {
        User mappedUser = beanMappingService.mapTo(userUpdate, User.class);
        userService.updateUser(userID, mappedUser);
    }

    @Override
    public void registerToStudyGroup(Long userID, Long studyGroupID) {
        userService.registerToStudyGroup(userID, studyGroupID);
    }

    @Override
    public void unregisterFromStudyGroup(Long userID, Long studyGroupID) {
        userService.unregisterFromStudyGroup(userID, studyGroupID);
    }

    @Override
    public List<StudyGroupDTO> findUserStudyGroups(Long userID) {
        return userService.findUserStudyGroups(userID)
                .stream()
                .map(studygroup -> beanMappingService.mapTo(studygroup, StudyGroupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(user -> beanMappingService.mapTo(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findUserByID(Long id) {
        Optional<User> user = userService.findUserByID(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        UserDTO mappedUser = beanMappingService.mapTo(user.get(), UserDTO.class);
        return Optional.of(mappedUser);
    }

    @Override
    public Optional<UserDTO> findUserByEmail(String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        UserDTO mappedUser = beanMappingService.mapTo(user.get(), UserDTO.class);
        return Optional.of(mappedUser);
    }
}
