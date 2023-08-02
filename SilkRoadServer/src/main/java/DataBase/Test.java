package DataBase;

import java.sql.SQLException;
import java.util.List;

import static DataBase.Ads.AccessoriesDB.selectDigitalAccessories;
import static DataBase.Ads.AppliancesDB.selectAppliances;
import static DataBase.Ads.Buildings.BuildingForRent.selectBuildingsForRent;
import static DataBase.Ads.Buildings.BuildingForRent.selectBuildingsForRentByMortgage;
import static DataBase.Ads.Buildings.BuildingsForSale.selectBuildingsForSale;
import static DataBase.Ads.Buildings.Lands.selectLands;
import static DataBase.Ads.ComputersDB.selectComputers;
import static DataBase.Ads.DigitalAppliancesDB.selectDigitalAppliances;
import static DataBase.Ads.GamingConsolesDB.selectGamingConsoles;
import static DataBase.Ads.IndustrialEquipments.selectIndustrialEquipments;
import static DataBase.Ads.PersonalItems.selectPersonalItems;
import static DataBase.Ads.PhonesAndTabletsDB.selectPhoneAndTablets;
import static DataBase.Ads.Vehicle.Cars.selectCars;

public class Test {
    public static void main(String[] args) throws SQLException {
        /*
        String send = "{\"title\" : \"xbox\", \"price\" : \"10000000\", \"city\" : \"khomein\", \"ownerID\" : 1, \"brand\" : \"xBox\"" +
                ", \"description\" : null, \"memory\" : 4, \"numberOfControllers\" : 2, \"isNew\" : \"yes\"" +
                ", \"image\" : null}";
        JSONObject jsonObject = new JSONObject(send);
        GamingConsolesDB.insertGamingConsole(jsonObject);


        send = "{\"title\" : \"appliance\", \"price\" : \"626200\", \"city\" : \"tehran\", \"ownerID\" : 5, \"brand\" : \"appbrand\"" +
                ", \"description\" : \"this is a test\", \"color\" : \"red\", \"isNew\" : \"no\"" +
                ", \"image\" : null}";
        jsonObject = new JSONObject(send);
        AppliancesDB.insertAppliances(jsonObject);

        send = "{\"title\" : \"b1\", \"price\" : \"0\", \"city\" : \"rasht\", \"ownerID\" : 7, \"area\" : \"400000\"" +
                ", \"description\" : \"this is a test\", \"isStore\" : \"no\", \"yearOfBuild\" : 1380" +
                ", \"image\" : null, \"rent\" : \"300000\", \"mortgage\" : \"62623\"}";
        jsonObject = new JSONObject(send);
        BuildingForRent.insertBuildingForRent(jsonObject);


        send = "{\"title\" : \"b2\", \"price\" : \"5378386\", \"city\" : \"tehran\", \"ownerID\" : 55, \"area\" : \"780000\"" +
                ", \"description\" : \"testbuilding\", \"isStore\" : \"no\", \"yearOfBuild\" : 1367" +
                ", \"image\" : null}";
        jsonObject = new JSONObject(send);
        BuildingsForSale.insertBuildingForSale(jsonObject);


        send = "{\"title\" : \"land\", \"price\" : \"36\", \"city\" : \"gorgan\", \"ownerID\" : 9, \"area\" : \"20004\"" +
                ", \"description\" : \"test land\", \"image\" : null}";
        jsonObject = new JSONObject(send);
        Lands.insertLands(jsonObject);


        send = "{\"title\" : \"car\", \"price\" : \"167\", \"city\" : \"esfhan\", \"ownerID\" : 88, \"brand\" : \"pride\"" +
                ", \"description\" : \"test car\", \"image\" : null, \"color\" : \"white\", \"kilometer\" : 6363, \"hasPaintJob\" : \"true\"" +
                ", \"yearOfBuild\" : 1381}";
        jsonObject = new JSONObject(send);
        Cars.insertCars(jsonObject);


        send = "{\"title\" : \"headset\", \"price\" : \"585\", \"city\" : \"rasht\", \"ownerID\" : 48, \"brand\" : \"samsung\"" +
                ", \"description\" : \"test accsries\", \"image\" : null, \"isNew\" : \"true\"}";
        jsonObject = new JSONObject(send);
        AccessoriesDB.insertAccessories(jsonObject);


        send = "{\"title\" : \"pc\", \"price\" : \"832\", \"city\" : \"qom\", \"ownerID\" : 23, \"brand\" : \"del\"" +
                ", \"description\" : \"test pc\", \"image\" : null, \"isNew\" : \"true\", \"RAM\" : 16" +
                ", \"graphic\" : 4, \"OS\" : \"windows\"}";
        jsonObject = new JSONObject(send);
        ComputersDB.insertComputers(jsonObject);


        send = "{\"title\" : \"applicies\", \"price\" : \"49\", \"city\" : \"kish\", \"ownerID\" : 79, \"brand\" : \"lg\"" +
                ", \"description\" : \"test pc\", \"image\" : null, \"isNew\" : \"false\"}";
        jsonObject = new JSONObject(send);
        DigitalAppliancesDB.insertDigitalAppliances(jsonObject);


        send = "{\"title\" : \"digap\", \"price\" : \"85\", \"city\" : \"kabul\", \"ownerID\" : 9," +
                "\"description\" : \"test digap\", \"image\" : null}";
        jsonObject = new JSONObject(send);
        IndustrialEquipments.insertIndustrialEquipments(jsonObject);


        send = "{\"title\" : \"pitem\", \"price\" : \"15\", \"city\" : \"rasht\", \"ownerID\" : 89," +
                "\"description\" : \"test pitem\", \"image\" : null, \"isNew\" : \"true\"}";
        jsonObject = new JSONObject(send);
        PersonalItems.insertPersonalItem(jsonObject);




        String send = "{\"title\" : \"phone1\", \"price\" : \"63\", \"city\" : \"sari\", \"ownerID\" : 43," +
                "\"description\" : \"test phone\", \"image\" : null, \"isNew\" : \"true\", \"brand\" : \"lg\"," +
                "\"memory\" : 128, \"RAM\" : 8, \"numberOfSIMCards\" : 2, \"color\" : \"black\"}";
        JSONObject jsonObject = new JSONObject(send);
        PhonesAndTabletsDB.insertPhoneAndTablets(jsonObject);
        */



        List<String> strings = selectLands(0, 10);
        for (String s : strings) {
            System.out.println("land:  ");
            System.out.println(s);
        }
        strings = selectBuildingsForSale(0, 10);
        for (String s : strings) {
            System.out.println("building for sale:  ");
            System.out.println(s);
        }

        strings = selectBuildingsForRent(0, 10);
        for (String s : strings) {
            System.out.println("building for rent:  ");
            System.out.println(s);
        }
        strings = selectCars(0, 10);
        for (String s : strings) {
            System.out.println("car:  ");
            System.out.println(s);
        }
        strings = selectDigitalAccessories(0, 10);
        for (String s : strings) {
            System.out.println("accessory:  ");
            System.out.println(s);
        }
        strings = selectAppliances(0, 10);
        for (String s : strings) {

            System.out.println("appliance:  ");
            System.out.println(s);
        }
        strings = selectDigitalAppliances(0, 10);
        for (String s : strings) {

            System.out.println("dig appliance:  ");
            System.out.println(s);
        }
        strings = selectComputers(0, 10);
        for (String s : strings) {

            System.out.println("computer:  ");
            System.out.println(s);
        }
        strings = selectGamingConsoles(0, 10);
        for (String s : strings) {

            System.out.println("gaming console:  ");
            System.out.println(s);
        }
        strings = selectIndustrialEquipments(0, 10);
        for (String s : strings) {

            System.out.println("equipment:  ");
            System.out.println(s);
        }
        strings = selectPersonalItems(0, 10);
        for (String s : strings) {

            System.out.println("personal item:  ");
            System.out.println(s);
        }
        strings = selectPhoneAndTablets(0, 10);
        for (String s : strings) {

            System.out.println("phablet:  ");
            System.out.println(s);
        }


        List<String> building = selectBuildingsForRentByMortgage("50", "1999", 0, 10);
        for (String s : building) {
            System.out.println("building for rent:  ");
            System.out.println(s);
        }





        /* send = "{\"name\" : \"sarina\", \"lastName\" : \"b\", \"city\" : \"tehran\", \"email\" : \"ss\"," +
                "\"phoneNumber\" : \"test\", \"username\" : \"sarina\", \"password\" : \"asdf\", \"bookmarkedAds\" :" +
                " \"{38 : Computers, 72 : Cars}\"," +
                "\"lastAdsViewed\" : \"{75: Lands, 595 : Cars}\"}";
        jsonObject = new JSONObject(send);
        Accounts.insertAccs(jsonObject);

         */



        DatabaseManager.checkingAdsInactivity();



    }
}
