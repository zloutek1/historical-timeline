package cz.muni.fi.pa165.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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
    private MultipartFile image;
}
