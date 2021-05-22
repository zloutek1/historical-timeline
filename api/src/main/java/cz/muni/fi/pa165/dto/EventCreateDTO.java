package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Eva Krajíková
 */

@Data
public class EventCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    private LocalDate date;
    private String location;
    private String description;
    private String imageIdentifier;
}
