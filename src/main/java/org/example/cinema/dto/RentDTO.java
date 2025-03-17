package org.example.cinema.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class RentDTO {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Date date;
    private Double price;
}
