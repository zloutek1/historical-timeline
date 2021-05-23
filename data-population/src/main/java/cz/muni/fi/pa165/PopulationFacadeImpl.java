package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dto.UserRole;
import cz.muni.fi.pa165.entity.Comment;
import cz.muni.fi.pa165.entity.Event;
import cz.muni.fi.pa165.entity.StudyGroup;
import cz.muni.fi.pa165.entity.Timeline;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.CommentService;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.StudyGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Transactional
public class PopulationFacadeImpl implements PopulationFacade {

    @Inject
    private UserService userService;

    @Inject
    private StudyGroupService studyGroupService;

    @Inject
    private TimelineService timelineService;

    @Inject
    private CommentService commentService;

    @Inject
    private EventService eventService;

    @Override
    @SuppressWarnings("unused")
    public void populate() {
        User john = user("jcaraher2@home.pl", "John", "Caraher", "password", UserRole.STUDENT);
        User eloise = user("eshuryv@t.co", "Eloise", "Shury", "password", UserRole.STUDENT);
        User even = user("emacvean7@mozilla.com", "Even", "MacVean", "password", UserRole.STUDENT);
        User nissa = user("nkeelyp@vkontakte.ru", "Nissa", "Keely", "password", UserRole.TEACHER);
        User dean = user("droggerss@wikimedia.org", "Dean", "Roggers", "password", UserRole.ADMINISTRATOR);
        User sampleUser = user("user@secret.com", "User", "Name", "password", UserRole.STUDENT);
        User sampleTeacher = user("teacher@secret.com", "Teacher", "Name", "password", UserRole.TEACHER);
        User sampleAdmin = user("admin@secret.com", "Admin", "Name", "password", UserRole.ADMINISTRATOR);

        StudyGroup history = studyGroup("History", nissa);
        StudyGroup literature = studyGroup("Literature", nissa);

        history.addMember(john);
        history.addMember(eloise);
        history.addMember(nissa);
        literature.addMember(sampleUser);
        literature.addMember(nissa);

        Timeline ww2 = timeline("World War 2", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 9, 2), history);
        Timeline ww1 = timeline("World War 1", LocalDate.of(1914, 7, 28), LocalDate.of(1918, 11, 11), history);
        Timeline victorian_era = timeline("Victorian era", LocalDate.of(1837, 6, 20), LocalDate.of(1901, 1, 22), history);
        Timeline trojan_war = timeline("Trojan war", year(-1260), year(-1180), history);

        Timeline renaissance_lit = timeline("Renaissance", year(1500), year(1670), literature);
        Timeline romantic_lit = timeline("Romantic", year(1798), year(1870), literature);
        Timeline victorian_lit = timeline("Victorian", year(1837), year(1901), literature);

        event("Hitler becomes Chancellor", LocalDate.of(1933, 1, 30), "Germany", "Hitler was liked by the Germans so they elected him as Chancellor. He then became Chancellor and lead Germany into success.", "No Image", ww2);
        event("Invasion of Poland", LocalDate.of(1939, 9, 1), "Poland", "German invaded Poland and this started World War 2. Germany invaded only a few days after signing the Non-Aggression Pact.", "No Image", ww2);
        event("Atomic bombings of Japan", LocalDate.of(1945, 8, 6), "Japan", "Hiroshima and Nagasaki were bombed. The United States and the allies attacked Japan.", "No image", ww2);

        event("Franz Ferdinand assassination", LocalDate.of(1914, 6, 28), "Austria", "WW1 was triggered from the assassination of Archduke Franz ferdinand and his pregnant wife Sophie by a Serbian terrorist group.", "No Image", ww1);
        event("WW1 ends.", LocalDate.of(1918, 11, 11), "Germany", "German's ran out of soldiers, food and war material. Not only this but support for the Germans was disappearing.", "No Image", ww1);

        event("Victoria becomes Queen", LocalDate.of(1837, 2, 27), "England", "Victoria is named Queen of Great Britain and Ireland.", "No Image", victorian_era);
        event("US Civil War begins", LocalDate.of(1861, 2, 1), "United States", "The US Civil War begins. Also in this year, the Russian serfs were emancipated and Prince Albert died of typhoid.", "No image", victorian_era);
        event("Queen Victoria Dies", LocalDate.of(1901, 2, 1), "United Kingdom", "No description", "No image", victorian_era);

        event("Trojan War Begins", LocalDate.of(-1000, 1, 7), "Troy, Turkey", "No Description", "No Image", trojan_war);
        event("Gods begin to take sides", LocalDate.of(-1000, 1, 11), "Troy, Turkey", "Zeus favors the Trojans. He sends a lying dream to Agamemnon that said that he would win if he attacked.", "No Image", trojan_war);
        event("The Trojan Horse", LocalDate.of(-1000, 1, 16), "Troy, Turkey", "Odysseus creates a plan to build a giant wooden horse. He and some other Greeks hide inside of it.", "No Image", trojan_war);
        event("The Greek Army Destroys Troy", LocalDate.of(-1000, 1, 19), "Troy, Turkey", "No Description", "No Image", trojan_war);

        event("Hamlet", LocalDate.of(1599, 10, 14), "United Kingdom", "It is believed Shakespeare began his work on Hamlet this year, which was considered to one of his greatest plays of all times.", "No Image", renaissance_lit);

        event("Oliver Twist", LocalDate.of(1837, 3, 28), "England", "Charles Dickens publishes Oliver Twist", "No Image", victorian_lit);
        event("Alice in Wonderland", LocalDate.of(1865, 5, 6), "United Kingdom", "Lewis Carroll publishes Alice’s Adventures in Wonderland.", "No image", victorian_lit);
        event("War and Peace", LocalDate.of(1869, 8, 5), "Russia", "In Russia, Leo Tolstoy publishes the text War and Peace. This novel is the study of early 19th-century Russian society and is regarded as one of the world's greatest novels.", "No image", victorian_lit);
        event("Sherlock Holmes", LocalDate.of(1887, 2, 17), "United Kingdom", "Arthur Conan Doyle writes A Study in Scarlet, introducing Sherlock Holmes to the readers.", "No image", victorian_lit);

        comment("Hello there", LocalDateTime.of(LocalDate.of(2012, 11, 8), LocalTime.of(11, 44)), trojan_war, john);
        comment("Welcome, I hope that you find this fascinating", LocalDateTime.of(LocalDate.of(2012, 11, 10), LocalTime.of(15, 8)), trojan_war, nissa);
    }

    private LocalDate year(int y) {
        return LocalDate.of(y, 1, 1);
    }

    private User user(String email, String firstName, String lastName, String password, UserRole role) {
        User u = new User(email, firstName, lastName, null, role);
        userService.registerUser(u, password);
        return u;
    }

    private StudyGroup studyGroup(String name, User leader) {
        StudyGroup s = new StudyGroup(name);
        s.setLeader(leader);
        studyGroupService.create(s);
        return s;
    }

    private Timeline timeline(String name, LocalDate fromDate, LocalDate toDate, StudyGroup studyGroup) {
        Timeline t = new Timeline(name, fromDate, toDate, studyGroup);
        timelineService.create(t);
        return t;
    }

    private Comment comment(String text, LocalDateTime time, Timeline timeline, User author) {
        Comment c = new Comment(text, time);
        c.setTimeline(timeline);
        c.setAuthor(author);
        commentService.create(c);
        return c;
    }

    private Event event(String name, LocalDate date, String location, String description, String imageIdentifier, Timeline timeline) {
        Event e = new Event(name, date, location, description, imageIdentifier);
        e.addTimeline(timeline);
        timeline.addEvent(e);
        eventService.create(e);
        return e;
    }
}
