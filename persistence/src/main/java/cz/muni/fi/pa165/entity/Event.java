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
import javax.persistence.Lob;
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
@Setter
@NoArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate date;

    private String location;

    private String description;

    @Lob
    private byte[] image;

    @ManyToMany
    @ToString.Exclude
    private final List<Timeline> timelines = new ArrayList<>();


    public Event(@NotNull String name, LocalDate date, String location, String description, byte[] image) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public void addTimeline(Timeline timeline){
        timelines.add(timeline);
    }

    public void removeTimeline(Timeline timeline){
        timelines.remove(timeline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getName().equals(event.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
