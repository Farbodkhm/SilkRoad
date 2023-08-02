package ADS.RealEstate;

import ADS.AD;

import java.util.Date;

public abstract class RealEstate extends AD {

    private double area;


    public RealEstate(Long price, String title, String city,
                      String description, int ownerID, Date time, double area, int databaseID) {
        super(price, title, city, description, ownerID, time, databaseID);
        this.area = area;
    }

    public double getArea() {
        return area;
    }
}
