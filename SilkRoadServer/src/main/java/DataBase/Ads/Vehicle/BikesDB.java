package DataBase.Ads.Vehicle;

import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BikesDB {

    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static int insertBikes (JSONObject json) throws SQLException {

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
        System.out.println("40");
        String title = json.getString("title");
        System.out.println(title);
        String price = json.getString("price");
        System.out.println(price);
        String city = json.getString("city");
        System.out.println(city);
        int ownerID = json.getInt("ownerID");
        System.out.println(ownerID);
        String brand = json.getString("brand");
        System.out.println(brand);
        int size = json.getInt("size");
        System.out.println(size);


        String query = "INSERT INTO Ads (title, image, price, city, description, ownerID, brand, date," +
                "size, category, reports)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, imagePath);
        statement.setString(3, price);
        statement.setString(4, city);
        statement.setString(5, description);
        statement.setInt(6, ownerID);
        statement.setString(7, brand);
        statement.setString(8, time);
        statement.setInt(9, size);
        statement.setString(10, "Bikes");
        statement.setInt(11, 0);

        System.out.println("++++++++++");
        statement.executeUpdate();
        System.out.println("**********");

        Statement sttmnt = connection.createStatement();
        ResultSet resultSet = sttmnt.executeQuery("SELECT seq from sqlite_sequence WHERE name='Ads'");

        int row = resultSet.getInt("seq");

        connection.close();
        return row;
    }

}

