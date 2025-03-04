package org.example.cinema;

import org.example.cinema.entity.Movie;
import org.example.cinema.infrasturcture.core.annotations.Autowired;

public class CinemaWorkroom {
    @Autowired
    private Worker worker;

    public CinemaWorkroom() {
    }

    public void maintenance(Movie movie) {
        worker.checkMovie(movie);
    }
}
