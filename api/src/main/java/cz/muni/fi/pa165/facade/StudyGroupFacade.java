package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;

import java.util.Optional;

public interface StudyGroupFacade {
    Long createStudyGroup(StudyGroupCreateDTO studyGroup);
    void deleteStudyGroup(Long id);
    Optional<StudyGroupDTO> findById(Long id);
    Optional<StudyGroupDTO> findByName(String name);
}
