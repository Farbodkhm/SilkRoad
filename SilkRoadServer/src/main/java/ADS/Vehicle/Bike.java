package ADS.Vehicle;

import java.util.Date;

public class Bike extends Vehicle {

    private int size;


    public Bike(Long price, String title, String city, String description, int ownerID, Date time, int databaseID,
                String brand, int size) {
        super(price, title, city, description, ownerID, time, databaseID, brand);
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
