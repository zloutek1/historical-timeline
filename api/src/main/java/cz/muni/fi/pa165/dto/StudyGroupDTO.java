package cz.muni.fi.pa165.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class StudyGroupDTO {
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<UserShortDTO> members;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TimelineShortDTO> timelines;
}
