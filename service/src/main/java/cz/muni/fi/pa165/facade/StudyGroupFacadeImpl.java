package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.StudyGroupService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

@Service
@Transactional
public class StudyGroupFacadeImpl implements StudyGroupFacade {

    @Inject
    private StudyGroupService studyGroupService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createStudyGroup(@NonNull StudyGroupCreateDTO studyGroup) {
        StudyGroup studyGroupEntity = new StudyGroup(studyGroup.getName());
        studyGroupService.create(studyGroupEntity);
        return studyGroupEntity.getId();
    }

    @Override
    public void deleteStudyGroup(@NonNull Long id) {
        StudyGroup studyGroup = studyGroupService.findById(id)
                .orElseThrow(() -> new ServiceException("Study group with id " + id + " does not exist"));
        studyGroupService.delete(studyGroup);
    }

    @Override
    public Optional<StudyGroupDTO> findById(@NonNull Long id) {
        var studyGroup = studyGroupService.findById(id);
        if (studyGroup.isEmpty()) return Optional.empty();
        var studyGroupDto = beanMappingService.mapTo(studyGroup.get(), StudyGroupDTO.class);
        return Optional.of(studyGroupDto);
    }

    @Override
    public Optional<StudyGroupDTO> findByName(@NonNull String name) {
        var studyGroup = studyGroupService.findByName(name);
        if (studyGroup.isEmpty()) return Optional.empty();
        var studyGroupDto = beanMappingService.mapTo(studyGroup.get(), StudyGroupDTO.class);
        return Optional.of(studyGroupDto);
    }
}
