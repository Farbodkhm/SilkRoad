package DataBase.Ads;

import ADS.DigitalGoods.GamingConsole;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GamingConsolesDB
{
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }


    synchronized public static int insertGamingConsole (JSONObject json) throws SQLException {
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
        int memory = json.getInt("memory");
        int numOfControl = json.getInt("numberOfController");
        String isNew = String.valueOf(json.getBoolean("new"));

        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, brand, date, " +
                "memory, numberOfControllers, isNew, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, brand);
        statement.setString(8, time);
        statement.setInt(9, memory);
        statement.setInt(10, numOfControl);
        statement.setString(11, isNew);
        statement.setString(12, "GamingConsole");
        statement.setInt(13, 0);

        statement.executeUpdate();
        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }

    synchronized public static List<String> selectGamingConsoles (int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (select adID, title, image, price, city, description, ownerID, brand, date, memory," +
                " numberOfControllers, isNew from Ads WHERE category = \"GamingConsole\") s LIMIT " + from
                + ", " + to + ";";
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
                int memory = results.getInt("memory");
                int numberOfControllers = results.getInt("numberOfControllers");
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                GamingConsole newConsole = new GamingConsole(price, title, city, description, ownerID, date,
                        databaseID, isNew, brand, memory, numberOfControllers);

                Gson gson = new Gson();
                String json = gson.toJson(newConsole);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }

    synchronized public static List<String> selectGamingConsolesByPrice (String lowerBound, String upperBound, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (SELECT adID, title, image, price, city, description, ownerID, brand, date, memory," +
                " numberOfControllers, isNew FROM Ads WHERE category = \"GamingConsoles\" AND price BETWEEN " + lowerBound + " AND " +
                upperBound + ") s LIMIT " + from + ", " + to + ";";
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
                int memory = results.getInt("memory");
                int numberOfControllers = results.getInt("numberOfControllers");
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                GamingConsole newConsole = new GamingConsole(price, title, city, description, ownerID, date,
                        databaseID, isNew, brand, memory, numberOfControllers);

                Gson gson = new Gson();
                String json = gson.toJson(newConsole);

                jsons.add(json);
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        connection.close();
        return jsons;
    }


    synchronized public static List<String> selectGamingConsolesByCity (String inputCity, int from, int to) throws SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<String> jsons = new ArrayList<>();

        String sql = "SELECT * FROM (select adID, title, image, price, city, description, ownerID, brand, date, memory," +
                " numberOfControllers, isNew from Ads WHERE category = \"GamingConsole\" AND city = \"" +
                inputCity + "\") s LIMIT " + from
                + ", " + to + ";";
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
                int memory = results.getInt("memory");
                int numberOfControllers = results.getInt("numberOfControllers");
                boolean isNew = results.getBoolean("isNew");
                int databaseID = results.getInt("adID");

                GamingConsole newConsole = new GamingConsole(price, title, city, description, ownerID, date,
                        databaseID, isNew, brand, memory, numberOfControllers);

                Gson gson = new Gson();
                String json = gson.toJson(newConsole);

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
