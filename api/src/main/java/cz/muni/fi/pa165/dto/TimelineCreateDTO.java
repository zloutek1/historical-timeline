package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Tomáš Ljutenko
 */
@Data
public class TimelineCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Long studyGroupId;
}
