package org.example.cinema;

import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.core.annotations.Property;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParserMovieFromFile {

    @Property
    private String filmTypesFile;
    @Property
    private String filmsFile;
    @Property
    private String rentsFile;

    @Property
    private String FOLDER_PATH;

    public ParserMovieFromFile() {
    }

    @InitMethod
    public void init() {
        //TODO: ???
    }

    public void loadFiles(List<FilmType> filmTypes, List<Film> films, List<Rent> rents) {
        try {
            loadTypes(filmTypes);
            loadFilms(films, filmTypes);
            loadRents(rents, films);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTypes(List<FilmType> filmTypes) throws IOException {
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + filmTypesFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                filmTypes.add(createType(line));
            }
        }
    }

    private void loadFilms(List<Film> films, List<FilmType> filmTypes) throws IOException {
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + filmsFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                films.add(createFilm(line, filmTypes));
            }
        }
    }

    private void loadRents(List<Rent> rents, List<Film> films) throws IOException, ParseException {
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + rentsFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                rents.add(createRent(line, films));
            }
        }
    }

    private FilmType createType(String csvString) {
        List<String> data;

        if (csvString.contains("\"")) {
            data = new ArrayList<>(Arrays.asList(csvString.substring(0, csvString.indexOf('"') - 1).split(",")));
            data.add(csvString.substring(csvString.indexOf('"') + 1, csvString.length() - 1).replace(',', '.'));
        } else {
            data = new ArrayList<>(Arrays.asList(csvString.split(",")));
        }

        return new FilmType(Integer.parseInt(data.get(0)), data.get(1), Double.parseDouble(data.get(2)));
    }

    private Film createFilm(String csvString, List<FilmType> filmTypes) {
        List<String> data;

        if (csvString.contains("\"")) {
            data = new ArrayList<>(Arrays.asList(csvString.substring(0, csvString.indexOf('"') - 1).split(",")));
            data.add(csvString.substring(csvString.indexOf('"') + 1, csvString.length() - 1).replace(',', '.'));
        } else {
            data = new ArrayList<>(Arrays.asList(csvString.split(",")));
        }

        FilmType ft = filmTypes.stream().filter(type -> type.getId() == Integer.parseInt(data.get(1))).findFirst().orElseThrow(IllegalArgumentException::new);
        return new Film(Integer.parseInt(data.get(0)), ft, data.get(2), data.get(3), Double.parseDouble(data.get(4)), Integer.parseInt(data.get(5)), data.get(6), Color.valueOf(data.get(7).toUpperCase()));
    }

    private Rent createRent(String csvString, List<Film> films) throws ParseException {
        List<String> data;

        if (csvString.contains("\"")) {
            data = new ArrayList<>(Arrays.asList(csvString.substring(0, csvString.indexOf('"') - 1).split(",")));
            data.add(csvString.substring(csvString.indexOf('"') + 1, csvString.length() - 1).replace(',', '.'));
        } else {
            data = new ArrayList<>(Arrays.asList(csvString.split(",")));
        }

        Rent result = new Rent(Integer.parseInt(data.get(0)), new SimpleDateFormat("dd.MM.yy").parse(data.get(1)), Double.parseDouble(data.get(2)));
        films.stream().filter(film -> film.getId() == result.getFilmId()).findFirst().orElseThrow(IllegalArgumentException::new).addRent(result);
        return result;
    }
}
