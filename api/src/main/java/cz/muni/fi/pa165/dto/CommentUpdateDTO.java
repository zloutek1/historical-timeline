package cz.muni.fi.pa165.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentUpdateDTO {
    @NotNull
    private Long id;
    @NotNull
    private String text;
}
