package org.example.cinema;

import org.example.cinema.entity.Movie;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.threads.annotations.Schedule;
import org.example.cinema.service.MovieService;

public class Courier {
    @Schedule(delta = 10000)
    public void moviesToMaintenance(Context context) {
        MovieService movieService = context.getObject(MovieService.class);
        CinemaWorkroom workroom = context.getObject(CinemaWorkroom.class);
        for (Movie movie: movieService.getAll()) {
            workroom.maintenance(movie);
        }
        System.out.println("<=====================>");
    }
}
