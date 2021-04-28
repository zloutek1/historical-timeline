package cz.muni.fi.pa165.facade;



import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface UserFacade {
    Long registerUser(UserCreateDTO user, String unencryptedPassword);
    void setUserRole(UserDTO user, UserRole role);
    UserRole getUserRole(UserDTO user);
    Boolean authenticate(UserAuthenticateDTO user);

    void registerToStudyGroup(UserDTO user, Long studyGroupID);
    void unregisterFromStudyGroup(UserDTO user, Long studyGroupID);
    List<StudyGroupDTO> getUsersStudyGroups(UserDTO user);

    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserByID(Long id);
    Optional<UserDTO> getUserByEmail(String email);

}
