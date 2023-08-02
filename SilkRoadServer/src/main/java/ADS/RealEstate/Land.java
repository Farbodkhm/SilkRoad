package ADS.RealEstate;

import java.util.Date;

public class Land extends RealEstate{


    public Land (Long price, String title, String city, String description, int ownerID, Date time,
                 double area, int databaseID) {
        super(price, title, city, description, ownerID, time, area, databaseID);
    }

}
