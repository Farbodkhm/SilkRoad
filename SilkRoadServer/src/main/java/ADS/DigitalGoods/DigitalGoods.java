package ADS.DigitalGoods;

import ADS.AD;

import java.util.Date;

public abstract class DigitalGoods extends AD {

    private boolean isNew;
    private String  brand;


    public DigitalGoods(Long price, String title, String city, String description, int ownerID,
                              Date time, int databaseID, boolean isNew, String brand) {
        super(price, title, city, description, ownerID, time, databaseID);
        this.isNew = isNew;
        this.brand = brand;
    }

    public boolean isNew() {
        return isNew;
    }

    public String getBrand() {
        return brand;
    }
}
