package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.UserShortDTO;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Inject
    private TimelineFacade timelineFacade;

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model,
                       RedirectAttributes redirectAttributes) {
        LOG.debug("show timeline");
        Optional<TimelineDTO> timelineOpt = timelineFacade.findById(id);
        if( timelineOpt.isEmpty() ) {
            redirectAttributes.addFlashAttribute("alert_danger", "No timeline with id " + id);
            return "redirect:/home";
        }

        TimelineDTO timeline = timelineOpt.get();
        timeline.setEvents(events());
        timeline.setComments(comments());

        model.addAttribute("timeline", timeline);
        model.addAttribute("comment", new CommentDTO());
        return "timeline/view";
    }







    private List<EventDTO> events() {
        var e = new ArrayList<EventDTO>();
        e.add(event("Hitler becomes Chancellor", LocalDate.of(1933, 1, 30), "Germany", "Hitler was liked by the Germans so they elected him as Chancellor. He then became Chancellor and lead Germany into success.", "No Image"));
        e.add(event("Invasion of Poland", LocalDate.of(1939, 9, 1), "Poland", "German invaded Poland and this started World War 2. Germany invaded only a few days after signing the Non-Aggression Pact.", "No Image"));
        e.add(event("Atomic bombings of Japan", LocalDate.of(1945, 8, 6), "Japan", "Hiroshima and Nagasaki were bombed. The United States and the allies attacked Japan.", "No image"));
        return e;
    }

    private List<CommentDTO> comments() {
        var c = new ArrayList<CommentDTO>();
        c.add(comment("Hello there", LocalDateTime.of(LocalDate.of(2020, 12, 2), LocalTime.of(12, 22)), user("Will", "Smith")));
        c.add(comment("Fascinating", LocalDateTime.of(LocalDate.of(2020, 12, 3), LocalTime.of(12, 24)), user("Joe", "Blob")));
        return c;
    }

    private EventDTO event(String name, LocalDate date, String location, String description, String imageIdentifier) {
        EventDTO e = new EventDTO();
        e.setName(name);
        e.setDate(date);
        e.setLocation(location);
        e.setDescription(description);
        e.setImageIdentifier(imageIdentifier);
        return e;
    }

    private UserShortDTO user(String first, String last) {
        var u = new UserShortDTO();
        u.setFirstName(first);
        u.setLastName(last);
        return u;
    }

    private CommentDTO comment(String text, LocalDateTime time, UserShortDTO author) {
        CommentDTO c = new CommentDTO();
        c.setText(text);
        c.setTime(time);
        c.setAuthor(author);
        return c;
    }
}
