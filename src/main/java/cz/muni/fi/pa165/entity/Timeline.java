package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @Setter
    private String name;

    @Setter
    private LocalDate from;

    @Setter
    private LocalDate to;

    @ManyToOne(optional = false)
    @NotNull
    @Setter
    private StudyGroup studyGroup;

    //@OneToMany
    //private List<Comment> comments = new ArrayList<>();

    //@OneToMany
    //private List<Event> events = new ArrayList<>();

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
