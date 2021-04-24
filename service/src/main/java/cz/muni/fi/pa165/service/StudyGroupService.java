package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.StudyGroup;

import java.util.List;
import java.util.Optional;

public interface StudyGroupService {
    List<StudyGroup> getAllStudyGroups();
    Optional<StudyGroup> findStudyGroupById(Long id);
    Long createStudyGroup(StudyGroup studyGroup);
}
