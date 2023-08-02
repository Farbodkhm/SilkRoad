package ADS.Appliances;

import ADS.AD;

import java.util.Date;

public class Appliance extends AD {

    private boolean isNew;
    private String  color;

    public Appliance(Long price, String title, String city, String description, int ownerID, Date time,
                     int databaseID, boolean isNew, String color) {
        super(price, title, city, description, ownerID, time, databaseID);
        this.isNew = isNew;
        this.color = color;
    }

    public boolean isNew() {
        return isNew;
    }

    public String getColor() {
        return color;
    }
}
