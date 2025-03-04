package org.example.cinema.old;

public class TechnicalSpecialist {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    private TechnicalSpecialist() {}

    public static boolean validateManufactureYear(int year) {
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR && year < 10000;
    }

    public static boolean validateWeight(double weight) {
        return weight >= 0;
    }

    public static boolean validateColor (Color color) {
        return null != color;
    }

    public static boolean validateFilmType(FilmType type) {
        return null != type.getTypeName() && type.getTaxCoefficient() >= 0;
    }

    public static boolean validateRegistrationNumber(String number) {
        return null != number && number.matches("\\d{4}");
    }

    public static boolean validateName(String name) {
        return null != name && !name.isEmpty();
    }

    public static boolean validateAbstractFilmType(AbstractFilmType type) {
        return null != type.getFilmTypeName() && !type.getFilmTypeName().isEmpty() && type.getTaxCoefficient() > 0;
    }

    public static boolean validateFilm8Mm(Film8Mm film) {
        return validateAbstractFilmType(film) && film.getCapacity() > 0 && film.getSpeed() > 0;
    }

    public static boolean validateFilm8Mm(Film16Mm film) {
        return validateAbstractFilmType(film) && film.getCapacity() > 0 && film.getSpeed() > 0;
    }

    public static boolean validateFilm8Mm(Film32Mm film) {
        return validateAbstractFilmType(film) && film.getCapacity() > 0 && film.getSpeed() > 0;
    }
}
