package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;

import java.util.List;
import java.util.Optional;

public class StudyGroupFacadeImpl implements StudyGroupFacade {
    @Override
    public List<StudyGroupDTO> getAllStudyGroups() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<StudyGroupDTO> getStudyGroupById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long createStudyGroup(StudyGroupCreateDTO studyGroup) {
        throw new UnsupportedOperationException();
    }
}
