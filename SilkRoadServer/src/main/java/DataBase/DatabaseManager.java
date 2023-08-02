package DataBase;

import ADS.AD;
import ADS.Appliances.Appliance;
import ADS.DigitalGoods.*;
import ADS.IndustrialEquipment.IndustrialEquipment;
import ADS.PersonalItems.PersonalItem;
import ADS.RealEstate.Building;
import ADS.RealEstate.Land;
import ADS.Vehicle.Car;
import com.google.gson.Gson;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager {

    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");


    synchronized public static void deleteAd (int id) throws SQLException {
        connection = DriverManager.getConnection(url);
        try (PreparedStatement delete = connection.prepareStatement("DELETE FROM Ads WHERE adID = ?")) {
            delete.setInt(1, id);

            if (delete.executeUpdate() > 0)
                System.out.println("Record deleted successfully.");
            else
                System.out.println("Record not found.");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void updateImagePath (int adID, String imagePath) throws SQLException {
        connection = DriverManager.getConnection(url);
        String update = "UPDATE Ads SET image = ? WHERE adID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        Gson gson = new Gson();
        statement.setString(1, imagePath);
        statement.setInt(2, adID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    synchronized public static String getAdsImagePath (int adID) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT image FROM Ads WHERE adID = " + adID + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String imagePath = results.getString("image");

            connection.close();
            return imagePath;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static String getAdsCategory (int adID) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT category FROM Ads WHERE adID = " + adID + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String category = results.getString("category");

            connection.close();
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    synchronized public static AD getAdByID (int adID) throws ParseException, SQLException {
        connection = DriverManager.getConnection(url);
        String sql = "SELECT * FROM Ads WHERE adID = " + adID + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String title = results.getString("title");
            String city = results.getString("city");
            long price = results.getLong("price");
            String description = results.getString("description");
            int ownerID = results.getInt("ownerID");
            String dateSTR = results.getString("date");
            Date date = formatter.parse(dateSTR);
            double area = results.getDouble("area");
            boolean isStore = results.getBoolean("isStore");
            int yearOfBuild = results.getInt("yearOfBuild");
            long rent = results.getLong("rent");
            long mortgage = results.getLong("mortgage");
            String brand = results.getString("brand");
            String color = results.getString("color");
            int kilometer = results.getInt("kilometer");
            boolean hasPaintJob = results.getBoolean("hasPaintJob");
            boolean isNew = results.getBoolean("isNew");
            int RAM = results.getInt("RAM");
            int graphic = results.getInt("graphic");
            String operatingSystem = results.getString("operatingSystem");
            int memory = results.getInt("memory");
            int numberOfControllers = results.getInt("numberOfControllers");
            int numberOfSIMCards = results.getInt("numberOfSIMCards");
            int reports = results.getInt("reports");
            String category = results.getString("category");

            switch (category) {
                case ("GamingConsole"): {
                    GamingConsole gamingConsole = new GamingConsole(price, title, city, description, ownerID, date,
                            adID, isNew, brand, memory, numberOfControllers);
                    connection.close();
                    return gamingConsole;
                }
                case ("Appliances"): {
                    Appliance appliance = new Appliance(price, title, city, description, ownerID, date, adID,
                            isNew, color);
                    connection.close();
                    return appliance;
                }
                case("BuildingsForRent"): {
                    Building building = new Building(0L, title, city, description, ownerID, date, area,
                            yearOfBuild, mortgage, rent, isStore, adID);
                    connection.close();
                    return building;
                }
                case ("BuildingsForSale"): {
                    Building buildingForSale = new Building(price, title, city, description, ownerID, date, area,
                            yearOfBuild, 0, 0, isStore, adID);
                    connection.close();
                    return buildingForSale;
                }
                case ("Lands"): {
                    Land newLand = new Land(price, title, city, description, ownerID, date, area, adID);
                    connection.close();
                    return newLand;
                }
                case ("Cars"): {
                    Car newCar = new Car(price, title, city, description, ownerID, date, adID, brand, yearOfBuild,
                            color, kilometer, hasPaintJob);
                    connection.close();
                    return newCar;
                }
                case ("DigitalAccessories"): {
                    DigitalAccessories newAccessory = new DigitalAccessories(price, title, city, description, ownerID, date, adID, isNew, brand);
                    connection.close();
                    return newAccessory;
                }
                case ("Computers"): {
                    Computer newComputer = new Computer(price, title, city, description, ownerID, date, adID,
                            isNew, brand, RAM, graphic, operatingSystem);
                    connection.close();
                    return newComputer;
                }
                case ("DigitalAppliances"): {
                    DigitalAppliance newAppliance = new DigitalAppliance(price, title, city, description, ownerID, date,
                            adID, isNew, brand);
                    connection.close();
                    return newAppliance;
                }
                case ("IndustrialEquipments"): {
                    IndustrialEquipment newEquipment = new IndustrialEquipment(price, title, city, description, ownerID, date, adID);
                    connection.close();
                    return newEquipment;
                }
                case ("PersonalItems"): {
                    PersonalItem newItem = new PersonalItem(price, title, city, description, ownerID, date, adID, isNew);
                    connection.close();
                    return newItem;
                }
                case ("PhonesAndTablets"): {
                    PhoneAndTablet newPhablet = new PhoneAndTablet(price, title, city, description, ownerID, date, adID,
                            isNew, brand, color, RAM, memory, numberOfSIMCards);
                    connection.close();
                    return newPhablet;
                }
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.close();

        return null;
    }

    public static int countUsersAd (int accID) throws SQLException {
        connection = DriverManager.getConnection(url);

        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(adID) FROM Ads WHERE ownerID =" + accID;
        ResultSet count = statement.executeQuery(sql);

        count.next();
        int size = count.getInt(1);

        connection.close();
        return size;
    }

    synchronized public static List<String> getUsersADs (int accID, int from, int to) throws ParseException, SQLException {
        connection = DriverManager.getConnection(url);

        List<String> ads = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT * FROM Ads WHERE ownerID = " + accID + ") s LIMIT " +
                from + ", " + to + ";";

        System.out.println(sql);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while (results.next()) {
                Gson gson = new Gson();
                String title = results.getString("title");
                String city = results.getString("city");
                long price = results.getLong("price");
                String description = results.getString("description");
                int adID = results.getInt("adID");
                String dateSTR = results.getString("date");
                Date date = formatter.parse(dateSTR);
                int ownerID = results.getInt("ownerID");

                AD newAD = new AD(price, title, city, description, ownerID, date, adID);
                ads.add(gson.toJson(newAD));
            }

            connection.close();
            return ads;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void checkingAdsInactivity () throws SQLException {
        connection = DriverManager.getConnection(url);
        String sql = "SELECT date, adID FROM Ads";
        LocalDate today = LocalDate.now();
        LocalDate daysAgo = today.plusDays(-20);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet dateAndId = statement.executeQuery();
            while (dateAndId.next())
            {
                String[] dateAndTime = dateAndId.getString(1).split(" ");
                LocalDate AdsDate = LocalDate.parse(dateAndTime[0]);
                int id = dateAndId.getInt(2);

                if (AdsDate.isBefore(daysAgo))
                {
                    deleteAd(id);
                }
            }
            statement.close();
            connection.close();
        }catch (SQLException e)
        {
            e.getMessage();
        }
        connection.close();
    }

    synchronized public static List<String> selectAdsByCategoryAndPriceAndCity (List<String> cats, String inputCity, String lowerBound,
                                                                         String upperBound, int from, int to) throws SQLException {
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String categoriesQuery = "";
        if (! (cats.isEmpty())) {
            for (String cat : cats) {
                categoriesQuery += " category = \"" + cat + "\" OR";
            }
            //removing the last OR
            categoriesQuery = categoriesQuery.substring(1, categoriesQuery.lastIndexOf("O") - 1);
        }

        StringBuilder sql = new StringBuilder();

        if (! (cats.isEmpty()) && (inputCity == null) && (upperBound == null)) {
            System.out.println("case 0");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE " + categoriesQuery +
                    ") s LIMIT " + from + ", " + to + ";");
        }

        else if (! (cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null)) {
            System.out.println("case 1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && ! (upperBound == null)) {
            System.out.println("case 2");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }
        else if ((cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)) {
            System.out.println("case 3");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity + "\") s LIMIT " + from + ", " + to + ";");
        }
        else if ((cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null)) {
            System.out.println("case 4");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }
        else if ( !(cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)) {
            System.out.println("case 5");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\") s LIMIT " + from + ", " + to + ";");
        }
        else if ( !(cats.isEmpty()) && (inputCity == null) && ! (upperBound == null)) {
            System.out.println("case 6");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }
        else if ( (cats.isEmpty()) && (inputCity == null) && (upperBound == null)) {
            System.out.println("case 7");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads) s LIMIT " + from + ", " + to + ";");
        }

        System.out.println(String.valueOf(sql));
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.valueOf(sql));
            while (results.next())
            {
                String title = results.getString("title");
                Long price = results.getLong("price");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                int databaseID = results.getInt("adID");

                AD Ad = new AD(price, title, city, description, ownerID, date, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(Ad);

                jsons.add(json);
            }
            statement.close();
            results.close();

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectAdsByCategoryAndCityAndPriceAndTitle (List<String> cats, String inputCity, String lowerBound,
                                                                                String upperBound, String title, int from, int to) throws SQLException {
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String categoriesQuery = "";
        if (! (cats.isEmpty())) {
            for (String cat : cats) {
                categoriesQuery += " category = \"" + cat + "\" OR";
            }
            //removing the last OR
            categoriesQuery = categoriesQuery.substring(1, categoriesQuery.lastIndexOf("O") - 1);
        }

        StringBuilder sql = new StringBuilder();

        if (! (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && title == null) {
            System.out.println("case 0");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE " + categoriesQuery +
                    ") s LIMIT " + from + ", " + to + ";");
        }

        if (! (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && !(title == null)) {
            System.out.println("case 0-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE " + categoriesQuery +
                    " AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if (! (cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }

        else if (! (cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && !(title == null)) {
            System.out.println("case 1-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 2");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && !(title == null)) {
            System.out.println("case 2-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && (upperBound == null) && (title == null)) {
            System.out.println("case 3");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity + "\") s LIMIT " + from + ", " + to + ";");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && (upperBound == null) && !(title == null)) {
            System.out.println("case 3-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity + "\" AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 4");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && ! (title == null)) {
            System.out.println("case 4-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ( !(cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)&&  (title == null)) {
            System.out.println("case 5");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\") s LIMIT " + from + ", " + to + ";");
        }

        else if ( !(cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)&& ! (title == null)) {
            System.out.println("case 5-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity + "\" AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ( !(cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 6");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    " s LIMIT " + from + ", " + to + ";");
        }

        else if ( !(cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && ! (title == null)) {
            System.out.println("case 6-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND Price BETWEEN " + lowerBound + " AND " + upperBound +
                    " AND title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && (title == null)) {
            System.out.println("case 7");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads) s LIMIT " + from + ", " + to + ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && ! (title == null)) {
            System.out.println("case 7-1");
            sql = sql.append("SELECT * FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE title LIKE \"%" + title +
                    "%\") s LIMIT " + from + ", " + to + ";");
        }

        System.out.println(String.valueOf(sql));
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.valueOf(sql));
            while (results.next())
            {
                String dbtitle = results.getString("title");
                Long price = results.getLong("price");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                int databaseID = results.getInt("adID");

                AD Ad = new AD(price, dbtitle, city, description, ownerID, date, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(Ad);

                jsons.add(json);
            }
            statement.close();
            results.close();

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        connection.close();
        return jsons;
    }
    synchronized public static int countSearchResults (List<String> cats, String inputCity, String lowerBound,
                                                                                        String upperBound, String title) throws SQLException {
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String categoriesQuery = "";
        if (! (cats.isEmpty())) {
            for (String cat : cats) {
                categoriesQuery += " category = \"" + cat + "\" OR";
            }
            //removing the last OR
            categoriesQuery = categoriesQuery.substring(1, categoriesQuery.lastIndexOf("O") - 1);
        }

        StringBuilder sql = new StringBuilder();

        if (! (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && title == null) {
            System.out.println("case 0");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE " + categoriesQuery +
                    ");");
        }

        if (! (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && !(title == null)) {
            System.out.println("case 0-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE " + categoriesQuery +
                    " AND title LIKE \"%" + title +
                    "%\");");
        }

        else if (! (cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ");");
        }

        else if (! (cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && !(title == null)) {
            System.out.println("case 1-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 2");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    ";");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && !(title == null)) {
            System.out.println("case 2-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && (upperBound == null) && (title == null)) {
            System.out.println("case 3");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity + "\");");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && (upperBound == null) && !(title == null)) {
            System.out.println("case 3-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity + "\" AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 4");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    ";");
        }

        else if ((cats.isEmpty()) && ! (inputCity == null) && ! (upperBound == null) && ! (title == null)) {
            System.out.println("case 4-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE city = \"" + inputCity +
                    "\" AND Price BETWEEN " + lowerBound + " AND " + upperBound + " AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ( !(cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)&&  (title == null)) {
            System.out.println("case 5");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity +
                    "\");");
        }

        else if ( !(cats.isEmpty()) && ! (inputCity == null) && (upperBound == null)&& ! (title == null)) {
            System.out.println("case 5-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND city = \"" + inputCity + "\" AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ( !(cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && (title == null)) {
            System.out.println("case 6");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND Price BETWEEN " + lowerBound + " AND " + upperBound + ")" +
                    ";");
        }

        else if ( !(cats.isEmpty()) && (inputCity == null) && ! (upperBound == null) && ! (title == null)) {
            System.out.println("case 6-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE (" + categoriesQuery + ") AND Price BETWEEN " + lowerBound + " AND " + upperBound +
                    " AND title LIKE \"%" + title +
                    "%\");");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && (title == null)) {
            System.out.println("case 7");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads);");
        }

        else if ( (cats.isEmpty()) && (inputCity == null) && (upperBound == null) && ! (title == null)) {
            System.out.println("case 7-1");
            sql = sql.append("SELECT COUNT (adID) FROM (SELECT title, image, price, city, description, ownerID, date, adID" +
                    " FROM Ads WHERE title LIKE \"%" + title +
                    "%\");");
        }

        System.out.println(String.valueOf(sql));

        Statement statement = connection.createStatement();
        ResultSet count = statement.executeQuery(String.valueOf(sql));

        count.next();
        int size = count.getInt(1);

        connection.close();
        return size;
    }

    synchronized public static void updateReports (int adID, int reportNum) throws SQLException {
        connection = DriverManager.getConnection(url);
        String update = "UPDATE Ads SET reports = ? WHERE adID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        statement.setInt(1, reportNum);
        statement.setInt(2, adID);


        statement.executeUpdate();
        statement.close();
        connection.close();
    }


}
