package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * A service to work with user entities
 *
 * @author David Sevcik
 */
public interface UserService {
    /**
     * Computes password hash and stores user entity
     * @param user user to be stored
     * @param unencryptedPassword password of the user
     * */
    Long registerUser(User user, String unencryptedPassword);

    /**
     * Computes hash for given password and checks if it match user password hash
     * @param user user to be authenticated
     * @param unencryptedPassword  password to check
     * */
    Boolean authenticate(User user, String unencryptedPassword);

    /**
     * Change user password: Computes its hash and updates user entity
     * @param userID ID of user whose password is changed
     * @param unencryptedPassword new password
     */
    void changeUserPassword(Long userID, String unencryptedPassword);

    /**
     * Update user by given attributes
     * @param userID ID of user whose attributes are updated
     * @param userUpdate user object. Non null attributes are to be set
     * */
    void updateUser(Long userID, User userUpdate);

    /**
     * Delete user
     * @param user user object
     * */
    void deleteUser(User user);

    /**
     * Register given user to given study group
     * @param userID id of user
     * @param studyGroupID id of study goup
     * */
    void registerToStudyGroup(Long userID, Long studyGroupID);

    /**
     * Unregister given user from given studygroup
     * @param userID ID of the user
     * @param studyGroupID ID of the study group
     * */
    void unregisterFromStudyGroup(Long userID, Long studyGroupID);

    /**
     * Finds all study groups, which is given user registered in
     * @param userID ID of the user
     * */
    List<StudyGroup> findUserStudyGroups(Long userID);

    /**
     * Return list containing all of the users
     * */
    List<User> findAllUsers();

    /**
     * Finds user by given ID
     * @param userID ID of the user
     * */
    Optional<User> findUserByID(Long userID);

    /**
     * Finds user by given email
     * @param email email of the user
     * */
    Optional<User> findUserByEmail(String email);
}