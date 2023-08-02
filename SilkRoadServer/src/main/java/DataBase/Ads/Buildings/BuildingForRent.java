package DataBase.Ads.Buildings;

import ADS.RealEstate.Building;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuildingForRent
{

    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertBuildingForRent (JSONObject json) throws SQLException {
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
        String city = json.getString("city");
        int ownerID = json.getInt("ownerID");
        String area = json.getString("area");
        String isStore = String.valueOf(json.getBoolean("isStore"));
        int yearOfBuild = json.getInt("yearOfBuild");
        String rent = json.getString("rent");
        String mortgage = json.getString("mortgage");

        String query = "INSERT INTO Ads (title, image, city, description, ownerID, area, date, isStore" +
                ", yearOfBuild, rent, mortgage, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, city);
        statement.setString(4, description);
        statement.setInt(5, ownerID);
        statement.setString(6, area);
        statement.setString(7, time);
        statement.setString(8, isStore);
        statement.setInt(9, yearOfBuild);
        statement.setString(10, rent);
        statement.setString(11, mortgage);
        statement.setString(12, "BuildingsForRent");
        statement.setInt(13, 0);


        statement.executeUpdate();
        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }

    synchronized public static List<String> selectBuildingsForRent (int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, city, description, ownerID, area, date, isStore," +
                " yearOfBuild, rent, mortgage, adID FROM Ads WHERE category = \"BuildingsForRent\") s LIMIT " + from + ", " + to + ";";
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next())
            {
                String title = results.getString("title");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isStore = results.getBoolean("isStore");
                int yearOfBuild = results.getInt("yearOfBuild");
                long rent = results.getLong("rent");
                long mortgage = results.getLong("mortgage");
                int databaseID = results.getInt("adID");

                //it is for rent so doesn't have price
                Building newBuilding = new Building(0L, title, city, description, ownerID, date, area,
                        yearOfBuild, mortgage, rent, isStore, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newBuilding);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectBuildingsForRentByMortgage (String lowerBound, String upperBound, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, city, description, ownerID, area, date, isStore," +
                " yearOfBuild, rent, mortgage, adID FROM Ads where category = \"BuildingsForRent\" AND mortgage between "
                + lowerBound + " and " + upperBound + ") s LIMIT " + from + ", " + to + ";";
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while (results.next())
            {
                String title = results.getString("title");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isStore = results.getBoolean("isStore");
                int yearOfBuild = results.getInt("yearOfBuild");
                long rent = results.getLong("rent");
                long mortgage = results.getLong("mortgage");
                int databaseID = results.getInt("adID");

                //it is for rent so doesn't have price
                Building newBuilding = new Building(0L, title, city, description, ownerID, date, area,
                        yearOfBuild, mortgage, rent, isStore, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newBuilding);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return jsons;
    }


    synchronized public static List<String> selectBuildingsForRentByCity (String inputCity, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, city, description, ownerID, area, date, isStore," +
                " yearOfBuild, rent, mortgage, adID FROM Ads WHERE category = \"BuildingsForRent\"" +
                "AND city = \"" + inputCity + "\") s LIMIT " + from + ", " + to + ";";
        try  {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next())
            {
                String title = results.getString("title");
                String city = results.getString("city");
                String description = results.getString("description");
                int ownerID = results.getInt("ownerID");
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                boolean isStore = results.getBoolean("isStore");
                int yearOfBuild = results.getInt("yearOfBuild");
                long rent = results.getLong("rent");
                long mortgage = results.getLong("mortgage");
                int databaseID = results.getInt("adID");

                //it is for rent so doesn't have price
                Building newBuilding = new Building(0L, title, city, description, ownerID, date, area,
                        yearOfBuild, mortgage, rent, isStore, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newBuilding);

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
