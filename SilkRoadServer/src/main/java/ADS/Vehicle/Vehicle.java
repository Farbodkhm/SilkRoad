package ADS.Vehicle;

import ADS.AD;

import java.util.Date;

public abstract class Vehicle extends AD {
    private String brand;


    public Vehicle(Long price, String title, String city,
                   String description, int ownerID, Date time, int databaseID, String brand) {
        super(price, title, city, description, ownerID, time, databaseID);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
