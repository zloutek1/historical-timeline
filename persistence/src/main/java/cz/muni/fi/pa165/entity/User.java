package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.dto.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents user entity
 *
 * @author David Sevcik
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String passwordHash;

    @NotNull
    @Enumerated
    private UserRole role;

    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private final List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private final List<StudyGroup> studyGroups = new ArrayList<>();

    @OneToMany(mappedBy = "leader")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private final List<StudyGroup> leadedStudyGroups = new ArrayList<>();

    public User(@NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull String passwordHash, @NotNull UserRole role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public void addComment(Comment comment)
    {
        comments.add(comment);
    }

    public void removeComment(Comment comment)
    {
        comments.remove(comment);
    }

    public List<Comment> getComments()
    {
        return Collections.unmodifiableList(comments);
    }

    public void addLeadedStudyGroups(StudyGroup studyGroup)
    {
        leadedStudyGroups.add(studyGroup);
        studyGroup.setLeader(this);
    }

    public void removeLeadedStudyGroup(StudyGroup studyGroup)
    {
        leadedStudyGroups.remove(studyGroup);
        studyGroup.setLeader(null);
    }

    public List<StudyGroup> getLeadedStudyGroups()
    {
        return Collections.unmodifiableList(leadedStudyGroups);
    }

    public void addStudyGroup(StudyGroup group)
    {
        studyGroups.add(group);
        group.addMember(this);
    }

    public void removeStudyGroup(StudyGroup group)
    {
        studyGroups.remove(group);
        group.removeMember(this);
    }

    public List<StudyGroup> getStudyGroups()
    {
        return Collections.unmodifiableList(studyGroups);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
