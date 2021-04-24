package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;

import java.util.List;
import java.util.Optional;

public interface StudyGroupFacade {
    List<StudyGroupDTO> getAllStudyGroups();
    Optional<StudyGroupDTO> getStudyGroupById(Long id);
    Long createStudyGroup(StudyGroupCreateDTO studyGroup);
}
