package org.example.cinema.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieDTO {
    private Long id;
    private String title;
    private Integer year;
    private Long genreId;
    private String genreName;
}
