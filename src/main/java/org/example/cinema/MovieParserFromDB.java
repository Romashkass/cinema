package org.example.cinema;

import org.example.cinema.entity.Genre;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Rent;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.service.GenreService;
import org.example.cinema.service.MovieService;
import org.example.cinema.service.RentService;

import java.util.List;

public class MovieParserFromDB implements MovieParser {
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RentService rentService;

    @Override
    public List<Genre> loadGenres() {
        return genreService.getAll();
    }

    @Override
    public List<Movie> loadMovies() {
        return movieService.getAll();
    }

    @Override
    public List<Rent> loadRents() {
        return rentService.getAll();
    }
}
