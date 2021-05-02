package cz.muni.fi.pa165.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Tomáš Ljutenko
 */
@Data
public class TimelineDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private LocalDate fromDate;
    private LocalDate toDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CommentDTO> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EventDTO> events;
}
