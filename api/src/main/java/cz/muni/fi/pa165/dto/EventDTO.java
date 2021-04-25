package cz.muni.fi.pa165.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva Krajíková
 */

@Data
public class EventDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String location;
    private String description;
    private String imageIdentifier;
    @ToString.Exclude
    private final List<TimelineDTO> timelines = new ArrayList<>();
}
