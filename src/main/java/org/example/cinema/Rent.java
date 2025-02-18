package org.example.cinema;

import java.util.Date;

public class Rent {
    private int filmId;
    private Date date;
    private double price;

    public Rent() {
    }

    public Rent(int filmId, Date date, double price) {
        this.filmId = filmId;
        this.date = date;
        this.price = price;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "filmId=" + filmId +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
