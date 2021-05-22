package cz.muni.fi.pa165.mvc.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordModel {
    @NotNull
    private String oldPassword;
    @NotNull
    @Size(min = 6, max = 30)
    private String newPassword;
    @NotNull
    @Size(min = 6, max = 30)
    private String newPasswordRepeated;
}
