package DataBase.Ads;

import ADS.Appliances.Appliance;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AppliancesDB
{
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertAppliances (JSONObject json) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);

        String imagePath;
        String description;

        if (!json.isNull("image"))
        {
            imagePath = json.getString("image");
        }else {
            imagePath = "camera-icon-vector-21756895.jpg";
        }

        if (!json.isNull("description"))
        {
            description = json.getString("description");
        }else {
            description = null;
        }

        String title = json.getString("title");
        String price = json.getString("price");
        String city = json.getString("city");
        int ownerID = json.getInt("ownerID");
        String isNew = String.valueOf(json.getBoolean("new"));
        String color = json.getString("color");

        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, color, date, isNew, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, color);
        statement.setString(8, time);
        statement.setString(9, isNew);
        statement.setString(10, "Appliances");
        statement.setInt(11, 0);

        statement.executeUpdate();
        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }


    synchronized public static List<String> selectAppliances (int from, int to) throws SQLException {

        setTime();
        connection = DriverManager.getConnection(url);

        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, color, date, isNew, adID" +
                " from Ads WHERE category = \"Appliances\") s LIMIT " + from + ", " + to + ";";
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
                String color = results.getString("color");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                Appliance newAppliance = new Appliance(price, title, city, description, ownerID, date, databaseID,
                        isNew, color);

                Gson gson = new Gson();
                String json = gson.toJson(newAppliance);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectAppliancesByPrice (int from, int to, String lowerBound, String upperBound) throws SQLException {
        setTime();

        connection = DriverManager.getConnection(url);

        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, color, date, isNew, adID" +
                " FROM Ads WHERE category = \"Appliances\" AND Price BETWEEN " + lowerBound + " AND " + upperBound +
                ") s LIMIT " + from + ", " + to + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while (results.next())
            {
                String title = results.getString("title");
                Long price = results.getLong("price");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                String color = results.getString("color");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                Appliance newAppliance = new Appliance(price, title, city, description, ownerID, date, databaseID,
                        isNew, color);

                Gson gson = new Gson();
                String json = gson.toJson(newAppliance);

                jsons.add(json);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }


    synchronized public static List<String> selectAppliancesByCity (String inputCity, int from, int to) throws SQLException {

        setTime();
        connection = DriverManager.getConnection(url);

        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, color, date, isNew, adID" +
                " from Ads WHERE category = \"Appliances\" AND city = \"" + inputCity +
                "\") s LIMIT " + from + ", " + to + ";";
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
                String color = results.getString("color");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                Appliance newAppliance = new Appliance(price, title, city, description, ownerID, date, databaseID,
                        isNew, color);

                Gson gson = new Gson();
                String json = gson.toJson(newAppliance);

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
