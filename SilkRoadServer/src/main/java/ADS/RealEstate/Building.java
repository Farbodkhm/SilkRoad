package ADS.RealEstate;

import java.util.Date;


public class Building extends RealEstate{

    private int yearOfBuild;
    private long mortgage;
    private long     rent;
    private boolean isStore; // if it is true it is a house else it is a store.


    public Building(Long price, String title, String city, String description, int ownerID,
                    Date time, double area, int yearOfBuild, long mortgage, long rent, boolean isStore,
                    int databaseID) {
        super(price, title, city, description, ownerID, time, area, databaseID);
        this.yearOfBuild = yearOfBuild;
        this.mortgage = mortgage;
        this.rent = rent;
        this.isStore = isStore;
    }

    public int getYearOfBuild() {
        return yearOfBuild;
    }

    public long getMortgage() {
        return mortgage;
    }

    public long getRent() {
        return rent;
    }

    public boolean isStore() {
        return isStore;
    }
}
