package org.example.cinema.facade;

import org.example.cinema.entity.Rent;
import org.example.cinema.dto.RentDTO;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.service.MovieService;
import org.example.cinema.service.RentService;

import java.util.List;
import java.util.stream.Collectors;

public class RentDTOFacade {
    @Autowired
    private MovieService movieService;
    @Autowired
    private RentService rentService;

    public RentDTO get(Long id){
        return rentToRentDTO(rentService.get(id));
    }

    public List<RentDTO> getAll(){
        return rentService.getAll().stream().map(this::rentToRentDTO).collect(Collectors.toList());
    }

    public Long save(RentDTO rent){
        return rentService.save(rentDTOToRent(rent));
    }

    public Rent rentDTOToRent(RentDTO rentDTO) {
        return Rent.builder()
                .id(rentDTO.getId())
                .movieId(rentDTO.getMovieId())
                .date(rentDTO.getDate())
                .price(rentDTO.getPrice())
                .build();
    }

    public RentDTO rentToRentDTO(Rent rent) {
        return RentDTO.builder()
                .id(rent.getId())
                .movieId(rent.getMovieId())
                .movieTitle(movieService.get(rent.getMovieId()).getTitle())
                .date(rent.getDate())
                .price(rent.getPrice())
                .build();
    }
}
