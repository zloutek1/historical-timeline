package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.exceptions.ServiceException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Ondřej Machala
 */
@Service
public class StudyGroupServiceImpl implements StudyGroupService {

    @Inject
    private StudyGroupDao studyGroupDao;

    @Override
    public void create(@NonNull StudyGroup studyGroup) {
        try {
            studyGroupDao.create(studyGroup);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to create study group " + studyGroup, ex);
        }
    }

    @Override
    public void update(@NonNull StudyGroup studyGroup) {
        try {
            studyGroupDao.update(studyGroup);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to update study group " + studyGroup, ex);
        }
    }

    @Override
    public void delete(@NonNull StudyGroup studyGroup) {
        try {
            studyGroupDao.delete(studyGroup);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to delete study group " + studyGroup, ex);
        }
    }

    @Override
    public Optional<StudyGroup> findById(@NonNull Long id) {
        try {
            return studyGroupDao.findById(id);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to retrieve study group with id " + id, ex);
        }
    }

    @Override
    public Optional<StudyGroup> findByName(@NonNull String name) {
        try {
            return studyGroupDao.findByName(name);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to retrieve study group with name " + name, ex);
        }
    }

    @Override
    public List<StudyGroup> findAll() {
        try {
            return studyGroupDao.findAll();
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to retrieve all study groups", ex);
        }
    }

}
