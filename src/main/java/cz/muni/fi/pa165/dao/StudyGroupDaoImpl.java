package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public class StudyGroupDaoImpl extends CrudDaoImpl<StudyGroup, Long> implements StudyGroupDao {

    public StudyGroupDaoImpl() {
        super(StudyGroup.class);
    }

    @Override
    public Optional<StudyGroup> findByName(@NonNull String name) {
        try {
            StudyGroup studyGroup = entityManager.createQuery("SELECT sg FROM StudyGroup sg WHERE sg.name LIKE :name", StudyGroup.class)
                    .setParameter("name", '%' + name + '%')
                    .getSingleResult();
            return Optional.of(studyGroup);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
