package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author David Sevcik
 */
@Data
public class UserUpdateDTO {
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;
    @NotNull
    private UserRole role;
}
