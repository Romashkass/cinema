package org.example.cinema.entity;

import lombok.*;
import org.example.cinema.infrasturcture.dto.annotations.Column;
import org.example.cinema.infrasturcture.dto.annotations.ID;
import org.example.cinema.infrasturcture.dto.annotations.Table;

import java.util.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    @ID
    private Long id;
    @Column(name = "movieId")
    private Long movieId;
    @Column(name = "date")
    private Date date;
    @Column(name = "price")
    private Double price;
}
