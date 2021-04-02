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
    @Setter
    private String email;

    @NotNull
    @Setter
    private String firstName;

    @NotNull
    @Setter
    private String lastName;

    @NotNull
    @Setter
    private String passwordHash;

//    @OneToMany
//    private final List<Comment> comments = new ArrayList<Comment>();

//    @ManyToMany
//    private final List<StudyGroup> studyGroups = new ArrayList<StudyGroup>();

    public User() {
    }

    public User(Long id, @NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull String passwordHash) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
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
