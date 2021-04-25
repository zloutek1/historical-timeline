package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.entity.StudyGroup;
import lombok.NonNull;
import org.dozer.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupServiceImpl implements StudyGroupService {

    @Inject
    private StudyGroupDao studyGroupDao;

    @Override
    public List<StudyGroup> getAll() {
        return studyGroupDao.findAll();
    }

    @Override
    public Optional<StudyGroup> findById(@NonNull Long id) {
        return studyGroupDao.findById(id);
    }

    @Override
    public Optional<StudyGroup> findByName(@NonNull String name) {
        return studyGroupDao.findByName(name);
    }

    @Override
    public void create(@NonNull StudyGroup studyGroup) {
        studyGroupDao.create(studyGroup);
    }

    @Override
    public void update(@NonNull StudyGroup studyGroup) {
        studyGroupDao.update(studyGroup);
    }

    @Override
    public void delete(@NonNull StudyGroup studyGroup) {
        studyGroupDao.delete(studyGroup);
    }
}
