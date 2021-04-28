package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
public interface UserService {
    Long registerUser(User user, String unencryptedPassword);
    UserRole getUserRole(User user);
    void setUserRole(User user, UserRole role);
    Boolean authenticate(User user, String password);

    void registerToStudyGroup(User user, Long studyGroupID);
    void unregisterFromStudyGroup(User user, Long studyGroupID);
    List<StudyGroup> getUsersStudyGroups(User user);

    List<User> getAllUsers();
    Optional<User> getUserById(Long userID);
    Optional<User> getUserByEmail(String email);
}