package org.example.cinema.old;

import org.example.cinema.ParserMovieFromFile;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;

import java.util.*;

public class FilmsCollection {
    private List<FilmType> filmTypes;
    private List<Film> films;
    private List<Rent> rents;

    @Autowired
    private ParserMovieFromFile parser;

    public FilmsCollection() {
        filmTypes = new ArrayList<>();
        films = new ArrayList<>();
        rents = new ArrayList<>();
    }

    @InitMethod
    public void init() {
        parser.loadFiles(filmTypes, films, rents);
    }

    public List<FilmType> getFilmTypes() {
        return filmTypes;
    }

    public void setFilmTypes(List<FilmType> filmTypes) {
        this.filmTypes = filmTypes;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public ParserMovieFromFile getParser() {
        return parser;
    }

    public void setParser(ParserMovieFromFile parser) {
        this.parser = parser;
    }

    public void insert(int index, Film film) {
        try {
            films.add(index, film);
        } catch (IndexOutOfBoundsException ex) {
            films.add(film);
        }
    }

    public int delete(int index) {
        try {
            films.remove(index);
        } catch (IndexOutOfBoundsException ex) {
            return -1;
        }
        return -1;
    }

    public double sumTotalProfit() {
        double result = 0;
        for (Film film: films) {
            result += film.getTotalProfit();
        }
        return result;
    }

    public void display() {
        System.out.println(String.format("\n%-3s %-9s %-9s %-6s %-4s %-9s %-11s %-11s %-11s %-11s", "ID", "Name", "RegNumber", "Weight", "Year", "Genre", "Color", "TaxPerMonth", "TotalIncome", "TotalProfit"));
        for (Film film: films) {
            System.out.println(String.format("%-3d %-9s %-9s %-6.2f %-4d %-9s %-11s %-11.2f %-11.2f %-11.2f",
                    film.getId(), film.getName(), film.getRegistrationNumber(), film.getWeight(), film.getManufactureYear(),
                    film.getGenre(), film.getColor(), film.getCalcTaxPerMonth(), film.getTotalIncome(), film.getTotalProfit()));
        }
    }

    public void sort(Comparator<Film> comparator) {
        films.sort(comparator);
    }

}
