package cz.muni.fi.pa165.dto;

import lombok.Data;

/**
 * @author David Sevcik
 */
@Data
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private UserRole role;
}
