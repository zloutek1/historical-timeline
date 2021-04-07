package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


/**
 * Class representing a user.
 *
 * @author David Sevcik
 */
@Entity
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Setter
    private String email;

    @NotNull
    @Column(nullable = false)
    @Setter
    private String firstName;

    @NotNull
    @Column(nullable = false)
    @Setter
    private String lastName;

    @NotNull
    @Column(nullable = false)
    @Setter
    private String passwordHash;

    @OneToMany(mappedBy = "author")
    private final List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private final List<StudyGroup> studyGroups = new ArrayList<>();

    public User() {
    }

    public User(Long id, @NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull String passwordHash) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
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
