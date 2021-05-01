package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.StudyGroup;

import java.util.List;
import java.util.Optional;

public interface StudyGroupService {
    void create(StudyGroup studyGroup);
    void update(StudyGroup studyGroup);
    void delete(StudyGroup studyGroup);
    Optional<StudyGroup> findById(Long id);
    Optional<StudyGroup> findByName(String name);
    List<StudyGroup> findAll();
}
