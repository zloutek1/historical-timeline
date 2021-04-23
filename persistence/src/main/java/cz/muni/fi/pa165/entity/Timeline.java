package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate toDate;

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

    public Timeline(@NotNull String name, @NotNull LocalDate fromDate, @NotNull LocalDate toDate, @NotNull StudyGroup studyGroup) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.studyGroup = studyGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline)) return false;
        Timeline timeline = (Timeline) o;
        return getName().equals(timeline.getName()) && Objects.equals(getFromDate(), timeline.getFromDate()) && Objects.equals(getToDate(), timeline.getToDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getFromDate(), getToDate());
    }
}
