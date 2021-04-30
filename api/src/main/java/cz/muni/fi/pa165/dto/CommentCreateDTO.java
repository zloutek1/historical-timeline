package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentCreateDTO {
    @NotNull
    private Long timelineId;
    @NotNull
    private Long userId;
    @NotNull
    private String text;
}
