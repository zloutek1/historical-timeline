package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dto.StudyGroupCreateDTO;
import cz.muni.fi.pa165.dto.StudyGroupDTO;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.StudyGroupService;
import cz.muni.fi.pa165.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class StudyGroupFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private StudyGroupService studyGroupService;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private UserService userService;

    @InjectMocks
    private StudyGroupFacade studyGroupFacade = new StudyGroupFacadeImpl();

    private User leader;

    private StudyGroup studyGroup;
    private StudyGroupDTO studyGroupDTO;


    private AutoCloseable closeable;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        leader = new User("foo@bar.cz", "foo", "bar", "some_hash", UserRole.TEACHER);
        leader.setId(1L);

        studyGroup = new StudyGroup("A1");
        studyGroup.setId(2l);
        studyGroupDTO = new StudyGroupDTO();
        studyGroupDTO.setName("A1");
        studyGroupDTO.setId(2l);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void findById_givenValidId_returnDTO() {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        when(beanMappingService.mapTo(studyGroup, StudyGroupDTO.class)).thenReturn(studyGroupDTO);
        var sgDTO = studyGroupFacade.findById(studyGroup.getId());
        assertThat(sgDTO).contains(studyGroupDTO);
    }

    @Test
    public void findByName_givenValidName_returnDTO() {
        when(studyGroupService.findByName(studyGroup.getName())).thenReturn(Optional.of(studyGroup));
        when(beanMappingService.mapTo(studyGroup, StudyGroupDTO.class)).thenReturn(studyGroupDTO);
        var sgDTO = studyGroupFacade.findByName(studyGroup.getName());
        assertThat(sgDTO).contains(studyGroupDTO);
    }

    @Test
    public void create_givenValidDTO_createEntity() {
        when(userService.findUserByID(leader.getId())).thenReturn(Optional.of(leader));
        var studyGroupCreateDTO = new StudyGroupCreateDTO();
        studyGroupCreateDTO.setName("B1");
        studyGroupCreateDTO.setLeader(leader.getId());
        studyGroupFacade.createStudyGroup(studyGroupCreateDTO);
        verify(studyGroupService).create(new StudyGroup("B1"));
    }

    @Test
    public void delete_givenValidId_deleteEntity() {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        studyGroupFacade.deleteStudyGroup(studyGroup.getId());
        verify(studyGroupService).delete(studyGroup);
    }
}
