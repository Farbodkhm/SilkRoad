package ADS.DigitalGoods;

import java.util.Date;

public class GamingConsole extends DigitalGoods{

    private int              memory;
    private int numberOfControllers;



    public GamingConsole(Long price, String title, String city, String description, int ownerID, Date time,
                         int databaseID, boolean isNew, String brand, int memory, int numberOfController) {
        super(price, title, city, description, ownerID, time, databaseID, isNew, brand);
        this.memory = memory;
        this.numberOfControllers = numberOfController;
    }

    public int getMemory() {
        return memory;
    }

    public int getNumberOfControllers() {
        return numberOfControllers;
    }
}
