package org.example.cinema.old;

public abstract class AbstractFilmType implements Taxable {
    private String filmTypeName;
    private double taxCoefficient;

    public AbstractFilmType(String filmTypeName, double taxCoefficient) {
        this.filmTypeName = filmTypeName;
        this.taxCoefficient = taxCoefficient;
    }

    public String getFilmTypeName() {
        return filmTypeName;
    }

    public void setFilmTypeName(String filmTypeName) {
        this.filmTypeName = filmTypeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    @Override
    public String toString() {
        return filmTypeName + ", " + taxCoefficient;
    }
}
