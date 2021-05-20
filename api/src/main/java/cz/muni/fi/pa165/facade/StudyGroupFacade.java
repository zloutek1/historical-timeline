package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Facade for working with study groups
 *
 * @author Ondřej Machala
 */
public interface StudyGroupFacade {

    /**
     * Stores a study group
     * @param studyGroup information regarding study group creation
     * @return id of study group
     */
    Long createStudyGroup(StudyGroupCreateDTO studyGroup);

    /**
     * Deletes a study group
     * @param id of study group
     */
    void deleteStudyGroup(Long id);

    /**
     * Fetches study group by id
     * @param id of study group
     * @return study group if stored, else empty value
     */
    Optional<StudyGroupDTO> findById(Long id);

    /**
     * Fetches study group by name
     * @param name of study group
     * @return study group if stored, else empty value
     */
    Optional<StudyGroupDTO> findByName(String name);

    /**
     * Fetches all study groups that are present in db
     * @return List of studygroups
     */
    List<StudyGroupDTO> findAllStudyGroups();
}
