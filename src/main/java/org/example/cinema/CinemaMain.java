package org.example.cinema;

import org.example.cinema.entity.Genre;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Rent;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.service.GenreService;
import org.example.cinema.service.MovieService;
import org.example.cinema.service.RentService;

import java.util.*;

public class CinemaMain {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext("org.example.cinema", new HashMap<>());
        MoviesCollection collection = context.getObject(MoviesCollection.class);
        collection.display();

//        fill(context);
//        test(context);
        Courier courier = context.getObject(Courier.class);
        courier.moviesToMaintenance(context);
        while (true){}
        //        Thread.sleep(120000);
    }

    private static void test(ApplicationContext context) {
        GenreService genreService = context.getObject(GenreService.class);
        MovieService movieService = context.getObject(MovieService.class);
        RentService rentService = context.getObject(RentService.class);
        movieService.save(new Movie(1L, "a', 124, 1); DROP TABLE rents; INSERT INTO movies (title, year, genreId) values ('aa", 123, 1L));
    }

    private static void fill(ApplicationContext context) {
        GenreService genreService = context.getObject(GenreService.class);
        MovieService movieService = context.getObject(MovieService.class);
        RentService rentService = context.getObject(RentService.class);

        List<Genre> genres = List.of(
                new Genre(1L, "Боевик"),
                new Genre(2L, "Ужасы"),
                new Genre(3L, "Драма"),
                new Genre(4L, "Мультфильм"),
                new Genre(5L, "Комедия")
        );
        for (Genre genre: genres) {
            genreService.save(genre);
        }
        List<Movie> movies = List.of(
                new Movie(1L, "Бэтмен", 2015, 1L),
                new Movie(1L, "Бэтмен 2", 2017, 1L),
                new Movie(1L, "Аватар", 2010, 1L),
                new Movie(1L, "Аватар 2", 2020, 1L),
                new Movie(1L, "Аватар 3", 2024, 1L),
                new Movie(1L, "Астрал", 2024, 2L),
                new Movie(1L, "Джокер", 2022, 1L),
                new Movie(1L, "Супермен возвращается", 2014, 1L),
                new Movie(1L, "Пираты силиконовой долины", 2019, 3L),
                new Movie(1L, "Гадкий я", 2006, 4L),
                new Movie(1L, "Армагеддон", 2019, 1L),
                new Movie(1L, "Полицейская академия", 2016, 5L),
                new Movie(1L, "Ограбление казино", 2020, 1L)
        );
        for (Movie movie: movies) {
            movieService.save(movie);
        }
        List<Rent> rents = List.of(
                new Rent(1L, 6L, new GregorianCalendar(2024, 9, 5).getTime(), 65.0),
                new Rent(1L, 18L, new GregorianCalendar(2025, 2, 5).getTime(), 180.5),
                new Rent(1L, 7L, new GregorianCalendar(2024, 3, 15).getTime(), 120.0),
                new Rent(1L, 8L, new GregorianCalendar(2024, 5, 20).getTime(), 85.5),
                new Rent(1L, 9L, new GregorianCalendar(2024, 7, 10).getTime(), 200.0),
                new Rent(1L, 10L, new GregorianCalendar(2024, 9, 25).getTime(), 150.0),
                new Rent(1L, 11L, new GregorianCalendar(2024, 11, 30).getTime(), 300.0),
                new Rent(1L, 12L, new GregorianCalendar(2025, 1, 5).getTime(), 250.5),
                new Rent(1L, 13L, new GregorianCalendar(2024, 4, 12).getTime(), 180.0),
                new Rent(1L, 14L, new GregorianCalendar(2024, 6, 18).getTime(), 90.0),
                new Rent(1L, 15L, new GregorianCalendar(2024, 8, 22).getTime(), 340.0),
                new Rent(1L, 16L, new GregorianCalendar(2024, 10, 15).getTime(), 75.0),
                new Rent(1L, 17L, new GregorianCalendar(2025, 2, 10).getTime(), 160.0),
                new Rent(1L, 6L, new GregorianCalendar(2024, 2, 28).getTime(), 65.0),
                new Rent(1L, 18L, new GregorianCalendar(2025, 2, 20).getTime(), 220.0),
                new Rent(1L, 7L, new GregorianCalendar(2024, 0, 2).getTime(), 130.0),
                new Rent(1L, 9L, new GregorianCalendar(2024, 1, 10).getTime(), 270.0)
        );
        for (Rent rent: rents) {
            rentService.save(rent);
        }
    }
}
