package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;

public interface StudyGroupDao extends CrudDao<StudyGroup, Long> {
    StudyGroup findByName(String name);
}
