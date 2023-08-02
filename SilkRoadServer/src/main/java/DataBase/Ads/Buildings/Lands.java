package DataBase.Ads.Buildings;

import ADS.RealEstate.Land;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Lands
{
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertLands (JSONObject json) throws SQLException {

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
        String area = String.valueOf(json.getDouble("area"));

        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, area, date, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, area);
        statement.setString(8, time);
        statement.setString(9, "Lands");
        statement.setInt(10, 0);

        statement.executeUpdate();
        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }

    synchronized public static List<String> selectLands (int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, area, date, adID" +
                " from Ads WHERE category = \"Lands\") s LIMIT " + from + ", " + to +
                ";";
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
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                int databaseID = results.getInt("adID");

                Land newLand = new Land(price, title, city, description, ownerID, date, area, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newLand);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }


    synchronized public static List<String> selectLandsByPrice (int from, int to, String lowerBound, String upperBound) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);

        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, area, date, adID" +
                " from Ads where price between " + lowerBound + " and " + upperBound + " AND category = \"Lands\"" +
                ") s LIMIT " + from + ", " + to + ";";
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
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                int databaseID = results.getInt("adID");

                Land newLand = new Land(price, title, city, description, ownerID, date, area, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newLand);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }


    synchronized public static List<String> selectLandsByCity (String inputCity, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, area, date, adID" +
                " from Ads WHERE category = \"Lands\" AND city = \"" + inputCity +
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
                double area = results.getDouble("area");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                int databaseID = results.getInt("adID");

                Land newLand = new Land(price, title, city, description, ownerID, date, area, databaseID);

                Gson gson = new Gson();
                String json = gson.toJson(newLand);

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
