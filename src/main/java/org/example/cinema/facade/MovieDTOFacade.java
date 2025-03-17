package org.example.cinema.facade;

import org.example.cinema.entity.Movie;
import org.example.cinema.dto.MovieDTO;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.service.GenreService;
import org.example.cinema.service.MovieService;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDTOFacade {
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreService genreService;

    public MovieDTO get(Long id){
        return movieToMovieDTO(movieService.get(id));
    }

    public List<MovieDTO> getAll(){
        return movieService.getAll().stream().map(this::movieToMovieDTO).collect(Collectors.toList());
    }

    public Long save(MovieDTO movie){
        return movieService.save(movieDTOToMovie(movie));
    }

    public Movie movieDTOToMovie(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .year(movieDTO.getYear())
                .genreId(movieDTO.getGenreId())
                .build();
    }

    public MovieDTO movieToMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .genreName(genreService.get(movie.getGenreId()).getName())
                .build();
    }
}
