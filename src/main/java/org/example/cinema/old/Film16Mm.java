package org.example.cinema.old;

public class Film16Mm extends AbstractFilmType {
    private double speed;
    private double capacity;

    public Film16Mm(double speed, double capacity) {
        super("16 mm", 0.2);
        this.speed = speed;
        this.capacity = capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public double getTaxPerMonth() {
        return 0;
    }
}
