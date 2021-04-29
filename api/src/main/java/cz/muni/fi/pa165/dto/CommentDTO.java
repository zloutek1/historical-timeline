package cz.muni.fi.pa165.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime time;
}
