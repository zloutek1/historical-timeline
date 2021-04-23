package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;

import java.util.Optional;

/**
 * @author Ondřej Machala
 */
public interface StudyGroupDao extends CrudDao<StudyGroup, Long> {

    /**
     * Finds the study group with the given name
     *
     * @param name name of the study group
     * @return the study group if present, else empty value
     */
    Optional<StudyGroup> findByName(String name);
}
