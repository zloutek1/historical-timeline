package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Setter
@ToString
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private LocalDate from;

    private LocalDate to;

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
