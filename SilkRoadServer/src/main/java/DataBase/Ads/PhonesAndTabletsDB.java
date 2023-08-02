package DataBase.Ads;

import ADS.DigitalGoods.PhoneAndTablet;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PhonesAndTabletsDB
{

    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertPhoneAndTablets (JSONObject json) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        String imagePath;
        String description;

        if (!json.isNull("image")) {
            imagePath = json.getString("image");
        } else {
            imagePath = "camera-icon-vector-21756895.jpg";
        }

        if (!json.isNull("description")) {
            description = json.getString("description");
        } else {
            description = null;
        }

        String title = json.getString("title");
        String price = json.getString("price");
        String city = json.getString("city");
        int ownerID = json.getInt("ownerID");
        String brand = json.getString("brand");
        String isNew = String.valueOf(json.getBoolean("new"));
        int memory = json.getInt("memory");
        int RAM = json.getInt("RAM");
        int numberOfSIMcards = json.getInt("numberOfSIMCards");
        String color = json.getString("color");

        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, brand, date" +
                ", isNew, memory, RAM, numberOfSIMCards, color, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, brand);
        statement.setString(8, time);
        statement.setString(9, isNew);
        statement.setInt(10, memory);
        statement.setInt(11, RAM);
        statement.setInt(12, numberOfSIMcards);
        statement.setString(13, color);
        statement.setString(14, "PhonesAndTablets");
        statement.setInt(15, 0);

        statement.executeUpdate();
        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }


    synchronized public static List<String> selectPhoneAndTablets (int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, isNew, memory, RAM," +
                " numberOfSIMCards, color, adID FROM Ads WHERE category = \"PhonesAndTablets\") s LIMIT " +
                from + ", " + to + ";";
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next())
            {
                String title = results.getString("title");
                Long price = results.getLong("price");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                String brand = results.getString("brand");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = results.getBoolean("isNew");
                int memory = results.getInt("memory");
                int RAM = results.getInt("RAM");
                int numberOfSIMCards = results.getInt("numberOfSIMCards");
                String color = results.getString("color");
                int databaseID = results.getInt("adID");

                PhoneAndTablet newPhablet = new PhoneAndTablet(price, title, city, description, ownerID, date, databaseID,
                        isNew, brand, color, RAM, memory, numberOfSIMCards);

                Gson gson = new Gson();
                String json = gson.toJson(newPhablet);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectPhoneAndTabletsByPrice (String lowerBound, String upperBound, int from , int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, isNew, memory, RAM," +
                " numberOfSIMCards, color, adID FROM Ads WHERE category = \"PhonesAndTablets AND Price BETWEEN\"" +
                " " + lowerBound + " AND " + upperBound + ") s LIMIT " + from + ", " + to + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                String title = result.getString("title");
                String imagePath = result.getString("image");
                String city = result.getString("city");
                Long price = result.getLong("price");
                String description = result.getString("description");
                int ownerID = result.getInt("ownerID");
                String brand = result.getString("brand");
                String dateStr = result.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = result.getBoolean("isNew");
                int memory = result.getInt("memory");
                int numberOfSIMcards = result.getInt("numberOfSIMCards");
                String color = result.getString("color");
                int databaseID = result.getInt("adID");
                int RAM = result.getInt("RAM");

                PhoneAndTablet phoneAndTablet = new PhoneAndTablet(price, title, city, description, ownerID,
                        date, databaseID, isNew, brand, color, RAM, memory, numberOfSIMcards);

                Gson gson = new Gson();
                String json = gson.toJson(phoneAndTablet);

                jsons.add(json);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }


    synchronized public static List<String> selectPhoneAndTabletsByCity (String inputCity, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, isNew, memory, RAM," +
                " numberOfSIMCards, color, adID FROM Ads WHERE category = \"PhonesAndTablets\" AND city = \"" +
                inputCity + "\") s LIMIT " + from + ", " + to + ";";
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next())
            {
                String title = results.getString("title");
                Long price = results.getLong("price");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                String brand = results.getString("brand");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = results.getBoolean("isNew");
                int memory = results.getInt("memory");
                int RAM = results.getInt("RAM");
                int numberOfSIMCards = results.getInt("numberOfSIMCards");
                String color = results.getString("color");
                int databaseID = results.getInt("adID");

                PhoneAndTablet newPhablet = new PhoneAndTablet(price, title, city, description, ownerID, date, databaseID,
                        isNew, brand, color, RAM, memory, numberOfSIMCards);

                Gson gson = new Gson();
                String json = gson.toJson(newPhablet);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

}
