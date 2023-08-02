package ADS.DigitalGoods;

import java.util.Date;

public class DigitalAccessories extends DigitalGoods{


    public DigitalAccessories(Long price, String title, String city, String description, int ownerID,
                              Date time, int databaseID, boolean isNew, String brand) {
        super(price, title, city, description, ownerID, time, databaseID, isNew, brand);
    }

}
