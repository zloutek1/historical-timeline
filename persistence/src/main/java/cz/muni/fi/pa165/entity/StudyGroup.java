package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a study group for {@link User users}.
 *
 * @author Ondřej Machala
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "studyGroups")
    @ToString.Exclude
    private final List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "studyGroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private final List<Timeline> timelines = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private User leader;

    public void addMember(User member)
    {
        members.add(member);
    }

    public void removeMember(User member)
    {
        members.remove(member);
    }

    public void addTimeline(Timeline timeline) {
        timelines.add(timeline);
    }

    public void removeTimeline(Timeline timeline) {
        timelines.remove(timeline);
    }

    public StudyGroup(@NotNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroup)) return false;
        StudyGroup that = (StudyGroup) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
