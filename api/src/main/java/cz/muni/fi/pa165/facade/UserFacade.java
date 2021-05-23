package cz.muni.fi.pa165.facade;



import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserCreateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Facade for work with user
 *
 * @author David Sevcik
 */
public interface UserFacade {
    /**
     * Computes password hash and stores user
     * @param user Information needed to create new user
     * */
    Long registerUser(UserCreateDTO user);

    /** Computes password hash and checks if it matches with user's password hash
     * @param user Information needed to user authentication
     * */
    Boolean authenticate(UserAuthenticateDTO user);

    /**
     * Change user password
     * @param userID ID of the user
     * @param unencryptedPassword new password
     * */
    void changeUserPassword(Long userID, String unencryptedPassword);

    /**
     * Updates user with given data
     * @param userID ID of the user
     * @param userUpdate data that should be changed
    * */
    void updateUser(Long userID, UserUpdateDTO userUpdate);

    /**
     * Deletes user with given id
     * @param userID ID of the user
     * */
    void deleteUser(Long userID);

    /**
     * Register given user to given study group
     * @param userID ID of the user
     * @param studyGroupID ID of the study group
     * */
    void registerToStudyGroup(Long userID, Long studyGroupID);

    /**
     * Unregister given user from given study group
     * @param userID ID of the user
     * @param studyGroupID ID of the study group
     * */
    void unregisterFromStudyGroup(Long userID, Long studyGroupID);

    /**
     * Find Study groups which is user regstered in
     * @param userID ID of the user
     * */
    List<StudyGroupDTO> findUserStudyGroups(Long userID);

    /**
     * Finds all study groups, which are lead by user
     * @param userID ID of the user
     * */
    List<StudyGroupDTO> findStudyGroupsLeadBy(Long userID);

    /**
     * Finds all of the users
     * */
    List<UserDTO> findAllUsers();

    /**
     * Finds user with given ID
     * @param id ID of the user
     * */
    Optional<UserDTO> findUserByID(Long id);

    /**
     * Finds user with given email
     * @param email email of the user
     * */
    Optional<UserDTO> findUserByEmail(String email);

}
