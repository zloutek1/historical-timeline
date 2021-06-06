package cz.muni.fi.pa165.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class TimelineAddEventDTO {
    @NotNull
    public Long timelineId;

    @NotNull
    public Long eventId;
}
