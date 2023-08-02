package ADS.Vehicle;

import java.util.Date;

public class Car extends Vehicle{

    private int       kilometer;
    private boolean hasPaintJob;
    private String        color;
    private int     yearOfBuild;


    public Car(Long price, String title, String city, String description, int ownerID, Date time,
               int databaseID, String brand, int yearOfBuild, String color, int kilometer, boolean hasPaintJob) {
        super(price, title, city, description, ownerID, time, databaseID, brand);
        this.kilometer = kilometer;
        this.hasPaintJob = hasPaintJob;
        this.color = color;
        this.yearOfBuild = yearOfBuild;
    }

    public int getKilometer() {
        return kilometer;
    }

    public boolean isHasPaintJob() {
        return hasPaintJob;
    }

    public String getColor() {
        return color;
    }

    public int getYearOfBuild() {
        return yearOfBuild;
    }
}
