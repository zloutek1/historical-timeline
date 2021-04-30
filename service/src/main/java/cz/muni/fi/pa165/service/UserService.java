package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface UserService {
    Long registerUser(User user, String unencryptedPassword);
    Boolean authenticate(User user, String unencryptedPassword);
    void changeUserPassword(Long userID, String unencryptedPassword);

    void updateUser(Long userID, User userUpdate);

    void registerToStudyGroup(Long userID, Long studyGroupID);
    void unregisterFromStudyGroup(Long userID, Long studyGroupID);
    List<StudyGroup> findUserStudyGroups(Long userID);

    List<User> findAllUsers();
    Optional<User> findUserByID(Long userID);
    Optional<User> findUserByEmail(String email);
}