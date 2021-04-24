package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StudyGroupCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
}
