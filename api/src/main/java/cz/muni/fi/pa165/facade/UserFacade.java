package cz.muni.fi.pa165.facade;



import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface UserFacade {
    Long registerUser(UserCreateDTO user, String unencryptedPassword);
    Boolean authenticate(UserAuthenticateDTO user);
    void changeUserPassword(Long userID, String unencryptedPassword);

    void updateUser(Long userID, UserUpdateDTO userUpdate);

    void registerToStudyGroup(Long userID, Long studyGroupID);
    void unregisterFromStudyGroup(Long userID, Long studyGroupID);
    List<StudyGroupDTO> findUserStudyGroups(Long userID);

    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserByID(Long id);
    Optional<UserDTO> findUserByEmail(String email);

}
