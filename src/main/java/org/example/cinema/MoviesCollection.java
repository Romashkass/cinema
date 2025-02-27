package org.example.cinema;

import lombok.NoArgsConstructor;
import org.example.cinema.entity.Genre;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Rent;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;

import java.util.List;

@NoArgsConstructor
public class MoviesCollection {
    private List<Genre> genres;
    private List<Movie> movies;
    private List<Rent> rents;

    @Autowired
    private MovieParser parser;

    @InitMethod
    public void init() {
        genres = parser.loadGenres();
        movies = parser.loadMovies();
        rents = parser.loadRents();
    }

    public void display() {
        System.out.println("Genres");
        System.out.println(String.format("%-3s %-15s", "ID", "Name"));
        for (Genre genre: genres) {
            System.out.println(String.format("%-3d %-15s", genre.getId(), genre.getName()));
        }

        System.out.println("\nMovies");
        System.out.println(String.format("%-3s %-30s %-4s %-11s", "ID", "Title", "Year", "Genre"));
        for (Movie movie: movies) {
            System.out.println(String.format("%-3d %-30s %-4d %-11s", movie.getId(), movie.getTitle(), movie.getYear(),
                    genres.stream().filter((o) -> o.getId() == movie.getGenreId()).findFirst().orElse(null).getName()));
        }

        System.out.println("\nRents");
        System.out.println(String.format("%-3s %-30s %-11s %-6s", "ID", "Movie", "Date", "Price"));
        for (Rent rent: rents) {
            System.out.println(String.format("%-3d %-30s %-11s %-6.2f", rent.getId(),
                    movies.stream().filter((o) -> o.getId() == rent.getMovieId()).findFirst().orElse(null).getTitle(),
                    rent.getDate(), rent.getPrice()));
        }
    }
}
