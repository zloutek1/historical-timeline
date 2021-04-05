package cz.muni.fi.pa165.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private LocalDate from;

    @NotNull
    @Column(nullable = false)
    private LocalDate to;

    @NotNull
    @ManyToOne(optional = false)
    private StudyGroup studyGroup;

    @ToString.Exclude
    @OneToMany(mappedBy = "timeline")
    private final List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "timelines")
    private final List<Event> events = new ArrayList<>();

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
