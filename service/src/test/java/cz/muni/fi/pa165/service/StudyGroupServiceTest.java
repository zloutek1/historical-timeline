package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.entity.StudyGroup;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertThrows;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class StudyGroupServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private StudyGroupDao studyGroupDao;

    @Autowired
    @InjectMocks
    private StudyGroupService studyGroupService;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void finish() throws Exception {
        closeable.close();
    }

    @Test
    public void create_givenStudyGroup_daoCreates() {
        StudyGroup studyGroup = new StudyGroup("A1");
        studyGroupService.create(studyGroup);
        verify(studyGroupDao).create(studyGroup);
    }

    @Test
    public void create_givenNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> studyGroupService.create(null));
    }
}
