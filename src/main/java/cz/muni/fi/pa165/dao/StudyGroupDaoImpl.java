package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class StudyGroupDaoImpl extends CrudDaoImpl<StudyGroup, Long> implements StudyGroupDao {

    public StudyGroupDaoImpl() {
        super(StudyGroup.class);
    }

    @Override
    public StudyGroup findByName(String name) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");
        try {
            return entityManager.createQuery("SELECT sg FROM StudyGroup sg WHERE sg.name LIKE :name", StudyGroup.class)
                    .setParameter("name", '%' + name + '%')
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
