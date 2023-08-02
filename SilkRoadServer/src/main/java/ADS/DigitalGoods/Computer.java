package ADS.DigitalGoods;

import java.util.Date;

public class Computer extends DigitalGoods{

    private int                RAM;
    private int            graphic;
    private String operatingSystem;



    public Computer(Long price, String title, String city, String description, int ownerID, Date time,
                        int databaseID, boolean isNew, String brand, int ram, int graphic, String operationSystem) {
        super(price, title, city, description, ownerID, time, databaseID, isNew, brand);
        this.RAM = ram;
        this.graphic = graphic;
        this.operatingSystem = operationSystem;
    }

    public int getRAM() {
        return RAM;
    }

    public int getGraphic() {
        return graphic;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}
