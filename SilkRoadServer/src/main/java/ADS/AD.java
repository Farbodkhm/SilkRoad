package ADS;

import java.util.Date;

public class AD {

    private Long            price;

    private String          title;
    private String           city;
    private String    description;
    private Date             date;

    private int           ownerID;
    private int        databaseID;


    public AD(Long price, String title, String city,
              String description, int ownerID, Date time, int databaseID) {
        this.price = price;
        this.title = title;
        this.city = city;
        this.description = description;
        this.date = time;
        this.ownerID = ownerID;
        this.databaseID = databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    public Long getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }


    public Date getDate() {
        return date;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public int getDatabaseID() {
        return databaseID;
    }

}
