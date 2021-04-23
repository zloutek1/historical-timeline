package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.StudyGroup;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ondřej Machala
 */
@Repository
public class StudyGroupDaoImpl extends CrudDaoImpl<StudyGroup, Long> implements StudyGroupDao {

    public StudyGroupDaoImpl() {
        super(StudyGroup.class);
    }

    @Override
    public Optional<StudyGroup> findByName(@NonNull String name) {
        return entityManager.createQuery("SELECT sg FROM StudyGroup sg WHERE sg.name LIKE :name", StudyGroup.class)
                .setParameter("name", '%' + name + '%')
                .getResultStream().findFirst();
    }
}
