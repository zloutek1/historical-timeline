package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class represents a comment under a timeline.
 *
 * @author Ondřej Machala
 */
@Entity
@Getter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @NotNull
    @Setter
    private String text;

    @NotNull
    @Setter
    private LocalDateTime time;

    @ManyToOne(optional = false)
    @NotNull
    @Setter
    private Timeline timeline;

    @ManyToOne(optional = false)
    @NotNull
    @Setter
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getText(), comment.getText()) && Objects.equals(getTime(), comment.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getTime());
    }
}
