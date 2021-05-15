package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author David Sevcik
 */
@Data
public class UserAuthenticateDTO {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
