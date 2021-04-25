package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;

import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface  UserFascade {
    Long registerUser(UserCreateDTO user, String unencryptedPassword);
    void setUserRole(UserDTO user, UserRole role);
    UserRole getUserRole(UserDTO user);
    Boolean authenticate(UserAuthenticateDTO user);

    void registerToStudyGroup(UserDTO user, Long studyGroupID);
    void unregisterFromStudyGroup(UserDTO user, Long studyGroupID);
    List<UserDTO> getAllUsersFromStudyGroup(UserDTO user, StudyGroupDTO studyGroup);

    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserByID(Long id);
    Optional<UserDTO> getUserByEmail(String email);

}
