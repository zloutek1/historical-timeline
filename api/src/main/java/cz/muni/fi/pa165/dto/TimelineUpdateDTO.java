package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TimelineUpdateDTO {
    @NotNull
    private Long id;

    private String name;
    private LocalDate fromDate;
    private LocalDate toDate;
}
