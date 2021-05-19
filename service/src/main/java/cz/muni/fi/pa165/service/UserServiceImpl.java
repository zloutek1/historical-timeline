package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author David Sevcik
 */
@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Inject
    private StudyGroupDao studyGroupDao;

    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public Long registerUser(@NonNull User user, @NonNull String unencryptedPassword) {
        user.setPasswordHash(encoder.encode(unencryptedPassword));
        Optional<User> createdUser;
        try {
            userDao.create(user);
            createdUser = userDao.findByEmail(user.getEmail());
        }
        catch (DataAccessException e) {
            throw new ServiceException("Cannot register user. ", e);
        }

        if (createdUser.isEmpty()) {
            throw new ServiceException("Could not persist User");
        }

        return createdUser.get().getId();
    }

    @Override
    public Boolean authenticate(@NonNull User user, @NonNull String unencryptedPassword) {
        return encoder.matches(unencryptedPassword, user.getPasswordHash());
    }

    @Override
    public void changeUserPassword(@NonNull Long userID, @NonNull String unencryptedPassword) {
        User user = findUserFromDaoIfExistsElseThrow(userID);
        user.setPasswordHash(encoder.encode(unencryptedPassword));
        try {
            userDao.update(user);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Cannot change password. ", e);
        }
    }

    @Override
    public void updateUser(@NonNull Long userID, @NonNull User userUpdate){
        User user = findUserFromDaoIfExistsElseThrow(userID);

        if (userUpdate.getFirstName() != null) {
            user.setFirstName(userUpdate.getFirstName());
        }

        if (userUpdate.getLastName() != null) {
            user.setLastName(userUpdate.getLastName());
        }

        if (userUpdate.getRole() != null) {
            user.setRole(userUpdate.getRole());
        }

        try {
            userDao.update(user);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Cannot update user. ", e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            userDao.delete(user);
        } catch (DataAccessException e) {
            throw new ServiceException("Cannot delete user. ", e);
        }
    }

    @Override
    public void registerToStudyGroup(@NonNull Long userID, @NonNull Long studyGroupID) {
        try {
            Optional<StudyGroup> studyGroup = studyGroupDao.findById(studyGroupID);
            if (studyGroup.isEmpty()) {
                throw new ServiceException("Could not register user to StudyGroup");
            }
            User user = findUserFromDaoIfExistsElseThrow(userID);
            user.addStudyGroup(studyGroup.get());

            userDao.update(user);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could nor reguster user from studygroup. ", e);
        }
    }

    @Override
    public void unregisterFromStudyGroup(@NonNull Long userID, @NonNull Long studyGroupID) {
        try {
            Optional<StudyGroup> studyGroup = studyGroupDao.findById(studyGroupID);


            if (studyGroup.isEmpty()) {
                throw new ServiceException("Could not unregister user from StudyGroup.");
            }
            User user = findUserFromDaoIfExistsElseThrow(userID);
            user.removeStudyGroup(studyGroup.get());

            userDao.update(user);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could not unregister user from StudyGroup. ", e);
        }
    }

    @Override
    public List<StudyGroup> findUserStudyGroups(@NonNull Long userID)
    {
        User user = findUserFromDaoIfExistsElseThrow(userID);
        return user.getStudyGroups();
    }

    @Override
    public List<User> findAllUsers() {
        try {
            return userDao.findAll();
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could not find list of users. ", e);
        }
    }

    @Override
    public Optional<User> findUserByID(@NonNull Long userID) {
        try {
            return userDao.findById(userID);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could not find user", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(@NonNull String email) {
        try {
            return userDao.findByEmail(email);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could not find user with email " + email + ". ", e);
        }

    }

    private User findUserFromDaoIfExistsElseThrow(Long userID) {
        Optional<User> user;
        try {
            user = userDao.findById(userID);
        }
        catch (DataAccessException e) {
            throw new ServiceException("Could not Find user. ", e);
        }

        if (user.isEmpty()) {
            throw new ServiceException("Could not Find user. ");
        }
        return user.get();
    }

}
