package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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
    @ToString.Exclude
    private final List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    private final List<StudyGroup> studyGroups = new ArrayList<>();

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
