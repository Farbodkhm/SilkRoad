package DataBase.Ads.Vehicle;

import ADS.Vehicle.Car;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cars
{
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertCars (JSONObject json) throws SQLException {

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
        String color = json.getString("color");
        int kilometer = json.getInt("kilometer");
        String hasPaintJob = String.valueOf(json.getBoolean("hasPaintJob"));
        int yearOfBuild = json.getInt("yearOfBuild");

        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, brand, date," +
                "color, kilometer, hasPaintJob, yearOfBuild, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, brand);
        statement.setString(8, time);
        statement.setString(9, color);
        statement.setInt(10, kilometer);
        statement.setString(11, hasPaintJob);
        statement.setInt(12, yearOfBuild);
        statement.setString(13, "Cars");
        statement.setInt(14, 0);

        statement.executeUpdate();

        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }

    synchronized public static List<String> selectCars (int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, color, kilometer, " +
                "hasPaintJob, yearOfBuild, adID FROM Ads WHERE category = \"Cars\") s LIMIT " + from + ", " + to + ";";
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
                String color = results.getString("color");
                int kilometer = results.getInt("kilometer");
                boolean hasPaintJob = results.getBoolean("hasPaintJob");
                int yearOfBuild = results.getInt("yearOfBuild");
                int databaseID = results.getInt("adID");

                Car newCar = new Car(price, title, city, description, ownerID, date, databaseID, brand, yearOfBuild,
                        color, kilometer, hasPaintJob);

                Gson gson = new Gson();
                String json = gson.toJson(newCar);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectCarsByPrice (int from, int to, String lowerBound, String upperBound) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, color, kilometer, " +
                "hasPaintJob, yearOfBuild, adID FROM Ads WHERE category = \"Cars\" AND price BETWEEN "
                + lowerBound + " AND " + upperBound + ") s LIMIT " + from + ", " + to + ";";
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
                String color = results.getString("color");
                int kilometer = results.getInt("kilometer");
                boolean hasPaintJob = results.getBoolean("hasPaintJob");
                int yearOfBuild = results.getInt("yearOfBuild");
                int databaseID = results.getInt("adID");

                Car newCar = new Car(price, title, city, description, ownerID, date, databaseID, brand, yearOfBuild,
                        color, kilometer, hasPaintJob);

                Gson gson = new Gson();
                String json = gson.toJson(newCar);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return jsons;
    }


    synchronized public static List<String> selectCarsByCity (String inputCity, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT title, image, price, city, description, ownerID, brand, date, color, kilometer, " +
                "hasPaintJob, yearOfBuild, adID FROM Ads WHERE category = \"Cars\" AND city = \"" + inputCity +
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
                String brand = results.getString("brand");
                String dateStr = results.getString("date");
                Date date = formatter.parse(dateStr);
                String color = results.getString("color");
                int kilometer = results.getInt("kilometer");
                boolean hasPaintJob = results.getBoolean("hasPaintJob");
                int yearOfBuild = results.getInt("yearOfBuild");
                int databaseID = results.getInt("adID");

                Car newCar = new Car(price, title, city, description, ownerID, date, databaseID, brand, yearOfBuild,
                        color, kilometer, hasPaintJob);

                Gson gson = new Gson();
                String json = gson.toJson(newCar);

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
