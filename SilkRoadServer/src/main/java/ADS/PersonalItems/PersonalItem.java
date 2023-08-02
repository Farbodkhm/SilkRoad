package ADS.PersonalItems;

import ADS.AD;

import java.util.Date;

public class PersonalItem extends AD {

    private boolean isNew;


    public PersonalItem(Long price, String title, String city, String description, int ownerID, Date time, int databaseID, boolean isNew) {
        super(price, title, city, description, ownerID, time, databaseID);
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }
}
