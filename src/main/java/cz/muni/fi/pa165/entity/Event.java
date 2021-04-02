package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a historical event.
 *
 * @author Eva Krajíková
 */
@Entity
@Getter
@ToString
public class Event {
    @Id
    @Setter
    private int id;

    @NotNull
    @Column(nullable = false, unique = true)
    @Setter
    private String name;

    @Setter
    private LocalDate date;

    @Setter
    private String location;

    @Setter
    private String description;

    @Setter
    private String imageIdentifier;

    @ManyToMany
    private final List<Timeline> timelines = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getName().equals(event.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
