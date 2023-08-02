package ADS.DigitalGoods;

import java.util.Date;

public class PhoneAndTablet extends DigitalGoods{

    private String         color;
    private int              RAM;
    private int           memory;
    private int numberOfSIMCards;



    public PhoneAndTablet(Long price, String title, String city, String description, int ownerID, Date time,
                          int databaseID, boolean isNew, String brand, String color, int ram, int memory,
                          int numberOfSIMCards) {
        super(price, title, city, description, ownerID, time, databaseID, isNew, brand);
        this.color = color;
        this.RAM = ram;
        this.memory = memory;
        this.numberOfSIMCards = numberOfSIMCards;
    }

    public String getColor() {
        return color;
    }

    public int getRAM() {
        return RAM;
    }

    public int getMemory() {
        return memory;
    }

    public int getNumberOfSIMCards() {
        return numberOfSIMCards;
    }
}
