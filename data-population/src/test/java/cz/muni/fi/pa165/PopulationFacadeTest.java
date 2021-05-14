package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.CommentDao;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.StudyGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(classes = {DataPopulationConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PopulationFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private StudyGroupDao studyGroupDao;

    @Autowired
    private TimelineDao timelineDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    public PopulationFacade populationFacade;

    @Test
    public void populate_insertsCorrectSizes() {
        assertThat(userService.findAllUsers()).hasSize(5);
        assertThat(studyGroupDao.findAll()).hasSize(2);
        assertThat(timelineDao.findAll()).hasSize(7);
        assertThat(commentDao.findAll()).hasSize(0);
        assertThat(eventDao.findAll()).hasSize(18);
    }

    @Test
    public void populate_insertsOneAdministrator() {
        var users = userService.findAllUsers();
        var admins = users.stream().filter(u -> u.getRole() == UserRole.ADMINISTRATOR).collect(Collectors.toList());
        assertThat(admins).hasSize(1);
        var admin = admins.get(0);
        assertTrue(userService.authenticate(admin, "AVeryStr0ngPa55word"));
    }
}
