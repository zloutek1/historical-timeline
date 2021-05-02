package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.StudyGroup;

import java.util.List;
import java.util.Optional;

/**
 * A service for working with study group entities
 *
 * @author Ondřej Machala
 */
public interface StudyGroupService {
    /**
     * Stores a study group entity
     * @param studyGroup to be stored
     */
    void create(StudyGroup studyGroup);

    /**
     * Updates a study group entity
     * @param studyGroup to be updated
     */
    void update(StudyGroup studyGroup);

    /**
     * Deletes a study group entity
     * @param studyGroup to be deleted
     */
    void delete(StudyGroup studyGroup);

    /**
     * Fetches study group by id
     * @param id of the study group to fetch
     * @return study group if found, else empty value
     */
    Optional<StudyGroup> findById(Long id);

    /**
     * Fetches study group by name
     * @param name of the study group to fetch
     * @return study group if found, else empty value
     */
    Optional<StudyGroup> findByName(String name);

    /**
     * Fetches all study groups
     * @return all stored study groups
     */
    List<StudyGroup> findAll();
}
