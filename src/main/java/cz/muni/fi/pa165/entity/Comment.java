package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class represents a comment under a timeline.
 *
 * @author Ondřej Machala
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment implements DbEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String text;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(optional = false)
    @NotNull
    @ToString.Exclude
    private Timeline timeline;

    @ManyToOne(optional = false)
    @NotNull
    @ToString.Exclude
    private User author;

    public Comment(@NotNull String text, @NotNull LocalDateTime time) {
        this.text = text;
        this.time = time;
    }

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
