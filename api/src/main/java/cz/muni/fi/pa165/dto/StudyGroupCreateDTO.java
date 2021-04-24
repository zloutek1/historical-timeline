package cz.muni.fi.pa165.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudyGroupCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
}
