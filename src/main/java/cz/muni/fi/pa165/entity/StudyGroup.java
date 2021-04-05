package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
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
public class StudyGroup implements DbEntity<Long> {
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
    @ToString.Exclude
    private final List<Timeline> timelines = new ArrayList<>();

    public void addMember(User member)
    {
        members.add(member);
    }

    public void removeMember(User member)
    {
        members.remove(member);
    }

    
    public StudyGroup() {}

    public StudyGroup(String name) {
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
