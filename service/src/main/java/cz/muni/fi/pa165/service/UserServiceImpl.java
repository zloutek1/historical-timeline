package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.dozer.inject.Inject;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Long registerUser(User user, String unencryptedPassword) {
        user.setPasswordHash(encoder.encode(unencryptedPassword));
        userDao.create(user);

        var createdUser = userDao.findByEmail(user.getEmail());
        if (createdUser.isPresent())
            return createdUser.get().getId();

        throw new ServiceException("Could not persist User");
    }

    @Override
    public UserRole getUserRole(User user) {
        return user.getRole();
    }

    @Override
    public void setUserRole(User user, UserRole role) {
        user.setRole(role);
        userDao.update(user);
    }

    @Override
    public Boolean authenticate(User user, String password) {
        return encoder.matches(password, user.getPasswordHash());
    }

    @Override
    public void registerToStudyGroup(User user, Long studyGroupID) {
        var studyGroup = studyGroupDao.findById(studyGroupID);
        if (studyGroup.isPresent()) {
            user.addStudyGroup(studyGroup.get());
            userDao.update(user);
            return;
        }
        throw new ServiceException("Could not register User to StudyGroup");
    }

    @Override
    public void unregisterFromStudyGroup(User user, Long studyGroupID) {
        var studyGroup = studyGroupDao.findById(studyGroupID);
        if (studyGroup.isPresent()) {
            user.removeStudyGroup(studyGroup.get());
            userDao.update(user);
            return;
        }
        throw new ServiceException("Could not unregister User from StudyGroup");
    }

    @Override
    public List<StudyGroup> getUsersStudyGroups(User user) {
        return user.getStudyGroups();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userID) {
        return userDao.findById(userID);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
