package org.example.cinema.entity;

import lombok.*;
import org.example.cinema.infrasturcture.dto.annotations.Column;
import org.example.cinema.infrasturcture.dto.annotations.ID;
import org.example.cinema.infrasturcture.dto.annotations.Table;

@Table(name = "genres")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @ID
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
}
