package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class StudyGroupServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private StudyGroupDao studyGroupDao;

    @Inject
    @InjectMocks
    private StudyGroupService studyGroupService;

    private AutoCloseable closeable;

    private StudyGroup studyGroup = new StudyGroup("A1");

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void create_givenStudyGroup_daoCreates() {
        studyGroupService.create(studyGroup);
        verify(studyGroupDao).create(studyGroup);
    }

    @Test
    public void create_givenDaoFails_throwsServiceException() {
        doThrow(mock(DataAccessException.class)).when(studyGroupDao).create(studyGroup);
        assertThrows(ServiceException.class, () -> studyGroupService.create(studyGroup));
    }

    @Test
    public void create_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.create(null));
    }

    @Test
    public void update_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.update(null));
    }

    @Test
    public void update_givenExistingStudyGroup_updates() {
        studyGroup.setName("B2");
        studyGroupService.update(studyGroup);
        verify(studyGroupDao).update(studyGroup);
    }

    @Test
    public void delete_givenStudyGroup_deletes() {
        studyGroupService.delete(studyGroup);
        verify(studyGroupDao).delete(studyGroup);
    }

    @Test
    public void delete_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.delete(null));
    }

    @Test
    public void findById_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.findById(null));
    }

    @Test
    public void findById_givenExistingId_returnsStudyGroup() {
        when(studyGroupDao.findById(3l)).thenReturn(Optional.of(studyGroup));
        var optionalStudyGroup = studyGroupService.findById(3l);
        assertThat(optionalStudyGroup).contains(studyGroup);
    }

    @Test
    public void findById_givenNonExistingId_returnsEmpty() {
        when(studyGroupDao.findById(3l)).thenReturn(Optional.empty());
        var optionalStudyGroup = studyGroupService.findById(3l);
        assertThat(optionalStudyGroup).isEmpty();
    }

    @Test
    public void findByName_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.findByName(null));
    }

    @Test
    public void findByName_givenExistingName_returnsStudyGroup() {
        when(studyGroupDao.findByName("A1")).thenReturn(Optional.of(studyGroup));
        var optionalStudyGroup = studyGroupService.findByName("A1");
        assertThat(optionalStudyGroup).contains(studyGroup);
    }

    @Test
    public void findAll_retrievesAll() {
        studyGroupService.findAll();
        verify(studyGroupDao).findAll();
    }
}
