package org.example.cinema;

import org.example.cinema.entity.Movie;

public class GoodWorker implements Worker {
    @Override
    public void checkMovie(Movie movie) {
        System.out.println("Checking movie '" + movie.getTitle() + "'");
    }
}
