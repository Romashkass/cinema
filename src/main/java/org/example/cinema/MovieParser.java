package org.example.cinema;

import org.example.cinema.entity.Genre;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Rent;

import java.util.List;

public interface MovieParser {
    List<Genre> loadGenres();
    List<Movie> loadMovies();
    List<Rent> loadRents();
}
