package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a historical timeline of {@link Event events}.
 *
 * @author Tomáš Ljutenko
 */
@Entity
@Getter
@ToString
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    @Setter
    private String name;

    @NotNull
    @Column(nullable = false)
    @Setter
    private LocalDate from;

    @NotNull
    @Column(nullable = false)
    @Setter
    private LocalDate to;

    @ManyToOne(optional = false)
    @NotNull
    @Setter
    private StudyGroup studyGroup;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "timelines")
    private List<Event> events = new ArrayList<>();

    public void addComment(Comment comment) { comments.add(comment); }

    public void removeComment(Comment comment) { comments.remove(comment); }

    public void addEvent(Event event) { events.add(event); }

    public void removeEvent(Event event) { events.remove(event); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline)) return false;
        Timeline timeline = (Timeline) o;
        return getName().equals(timeline.getName()) && Objects.equals(getFrom(), timeline.getFrom()) && Objects.equals(getTo(), timeline.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getFrom(), getTo());
    }
}
