package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author David Sevcik
 */
@Data
public class UserAuthenticateDTO {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;
}
