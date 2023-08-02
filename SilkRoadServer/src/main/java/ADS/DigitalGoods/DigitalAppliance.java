package ADS.DigitalGoods;

import java.util.Date;

public class DigitalAppliance extends DigitalGoods{

    public DigitalAppliance(Long price, String title, String city, String description, int ownerID,
                            Date time, int databaseID, boolean isNew, String brand) {
        super(price, title, city, description, ownerID, time, databaseID, isNew, brand);
    }
}
