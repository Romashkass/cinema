package org.example.cinema.entity;

import lombok.*;
import org.example.cinema.infrasturcture.dto.annotations.Column;
import org.example.cinema.infrasturcture.dto.annotations.ID;
import org.example.cinema.infrasturcture.dto.annotations.Table;

@Table(name = "movies")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @ID
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "year")
    private Integer year;
    @Column(name = "genreId")
    private Long genreId;
}
