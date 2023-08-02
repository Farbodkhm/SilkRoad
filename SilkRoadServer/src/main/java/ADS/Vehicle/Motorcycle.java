package ADS.Vehicle;

import java.util.Date;

public class Motorcycle extends Vehicle{

    private int   kilometer;
    private int yearOfBuild;

    public Motorcycle(Long price, String title, String city, String description, int ownerID, Date time, int databaseID, String brand, int kilometer, int yearOfBuild) {
        super(price, title, city, description, ownerID, time, databaseID, brand);
        this.kilometer = kilometer;
        this.yearOfBuild = yearOfBuild;
    }

    public int getKilometer() {
        return kilometer;
    }

    public int getYearOfBuild() {
        return yearOfBuild;
    }
}
