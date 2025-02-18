package org.example.cinema;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilmsCollection {
    private List<FilmType> filmTypes;
    private List<Film> films;
    private List<Rent> rents;

    private static final String FOLDER_PATH = "./src/main/resources/cinema/";

    public FilmsCollection(String filmTypesFileName, String filmsFileName, String rentsFileName) {

        try {
            filmTypes = loadTypes(filmTypesFileName);
            films = loadFilms(filmsFileName);
            rents = loadRents(rentsFileName);
        } catch (Exception e) {
            throw new IllegalArgumentException("takie dela", e);
        }
    }

    private List<FilmType> loadTypes(String inFile) throws IOException {
        List<FilmType> filmTypes = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + inFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                filmTypes.add(createType(line));
            }
        }
        return filmTypes;
    }

    private List<Rent> loadRents(String inFile) throws IOException, ParseException {
        List<Rent> rents = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + inFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                rents.add(createRent(line));
            }
        }
        return rents;
    }

    private List<Film> loadFilms(String inFile) throws IOException {
        List<Film> films = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(FOLDER_PATH + inFile);
            Scanner scanner = new Scanner(fis)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                films.add(createFilm(line));
            }
        }
        return films;
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

    private Film createFilm(String csvString) {
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

    private Rent createRent(String csvString) throws ParseException {
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
        /*System.out.println("films");
        for (Film film: films) {
            System.out.println(film);
        }
        System.out.println("filmTypes");
        for (FilmType filmType: filmTypes) {
            System.out.println(filmType);
        }
        System.out.println("rents");
        for (Rent rent: rents) {
            System.out.println(rent);
        }*/
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
