package org.example.cinema.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDTO {
    private Long id;
    private String name;
}
