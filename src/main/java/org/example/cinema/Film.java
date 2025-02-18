package org.example.cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film implements Comparable<Film> {
    private int id;
    private List<Rent> collection;
    private FilmType filmType;
    AbstractFilmType type;
    private String name;
    private String registrationNumber;
    private double weight;
    private int manufactureYear;
    private String genre;
    private Color color;

    public Film() {
        this.collection = new ArrayList<>();
        this.filmType = new FilmType();
        this.name = "unknown";
        this.registrationNumber = "-1";
        this.weight = -1;
        this.manufactureYear = -1;
        this.genre = "unknown";
        this.color = Color.BLACK_WHITE;
    }

    public Film(int id, FilmType filmType, String name, String registrationNumber, double weight, int manufactureYear, String genre, Color color) {
        if (!TechnicalSpecialist.validateFilmType(filmType)) {
            throw new IllegalArgumentException("Wrong film type: " + filmType);
        }
        if (!TechnicalSpecialist.validateName(name)){
            throw new IllegalArgumentException("Wrong name: " + name);
        }
        if (!TechnicalSpecialist.validateRegistrationNumber(registrationNumber)){
            throw new IllegalArgumentException("Wrong registration number: " + registrationNumber);
        }
        if (!TechnicalSpecialist.validateWeight(weight)){
            throw new IllegalArgumentException("Wrong weight: " + weight);
        }
        if (!TechnicalSpecialist.validateManufactureYear(manufactureYear)){
            throw new IllegalArgumentException("Wrong manufacture year: " + manufactureYear);
        }
        if (!TechnicalSpecialist.validateColor(color)){
            throw new IllegalArgumentException("Wrong color: " + color);
        }
        this.id = id;
        this.collection = new ArrayList<>();
        this.filmType = filmType;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.weight = weight;
        this.manufactureYear = manufactureYear;
        this.genre = genre;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rent> getCollection() {
        return collection;
    }

    public void setCollection(List<Rent> collection) {
        this.collection = collection;
    }

    public void addRent(Rent rent) {
        collection.add(rent);
    }

    public FilmType getFilmType() {
        return filmType;
    }

    public void setFilmType(FilmType filmType) {
        if (!TechnicalSpecialist.validateFilmType(filmType)) {
            throw new IllegalArgumentException("Wrong film type: " + filmType);
        }
        this.filmType = filmType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!TechnicalSpecialist.validateName(name)){
            throw new IllegalArgumentException("Wrong name: " + name);
        }
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (!TechnicalSpecialist.validateRegistrationNumber(registrationNumber)){
            throw new IllegalArgumentException("Wrong registration number: " + registrationNumber);
        }
        this.registrationNumber = registrationNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (!TechnicalSpecialist.validateWeight(weight)){
            throw new IllegalArgumentException("Wrong weight: " + weight);
        }
        this.weight = weight;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        if (!TechnicalSpecialist.validateManufactureYear(manufactureYear)){
            throw new IllegalArgumentException("Wrong manufacture year: " + manufactureYear);
        }
        this.manufactureYear = manufactureYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (!TechnicalSpecialist.validateColor(color)){
            throw new IllegalArgumentException("Wrong color: " + color);
        }
        this.color = color;
    }

    public double getTotalIncome() {
        double result = 0;
        for (Rent rent: collection) {
            result += rent.getPrice();
        }
        return result;
    }

    public double getTotalProfit() {
        return getTotalIncome() - getCalcTaxPerMonth();
    }

    public double getCalcTaxPerMonth() {
        return weight * 0.0013 + filmType.getTaxCoefficient() * 30 + 5;
    }

    @Override
    public String toString() {
        return filmType.getString() + ", " + name + ", " + registrationNumber + ", " + weight + ", " + manufactureYear + ", " + genre + ", " + color + ", " + getCalcTaxPerMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(filmType, film.filmType) && Objects.equals(name, film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmType, name);
    }

    @Override
    public int compareTo(Film o) {
        int result = Integer.compare(manufactureYear, o.manufactureYear);
        if (result == 0) {
            result = name.compareTo(o.name);
        }
        return result;
    }
}
