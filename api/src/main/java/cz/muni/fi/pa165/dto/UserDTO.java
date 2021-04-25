package cz.muni.fi.pa165.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Sevcik
 */
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private UserRole role;
    private final List<StudyGroupDTO> studyGroups = new ArrayList<>();
}
