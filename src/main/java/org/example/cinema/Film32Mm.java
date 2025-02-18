package org.example.cinema;

public class Film32Mm extends AbstractFilmType{
    private double speed;
    private double capacity;

    public Film32Mm(double speed, double capacity) {
        super("32 mm", 0.3);
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
