package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;

import java.util.Optional;

/**
 * @author Ondřej Machala
 */
public interface StudyGroupDao extends CrudDao<StudyGroup, Long> {
    Optional<StudyGroup> findByName(String name);
}
