package DataBase.Account;

import ADS.AD;
import Account.Account;
import DataBase.DatabaseManager;
import DataBase.MD5;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Accounts {
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    static {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("not found");
        }

        //getting time
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }


    synchronized public static boolean insertAccs(JSONObject json) throws SQLException {

        connection = DriverManager.getConnection(url);

        String imagePath;
        String description;

        if (!json.isNull("image")) {
            imagePath = json.getString("image");
        } else {
            imagePath = "free-profile-icon-25.jpg";
        }

        String name = json.getString("name");
        String lastName = json.getString("lastName");
        String email = json.getString("email");
        String phoneNumber = json.getString("phoneNumber");
        String city = json.getString("city");
        String username = json.getString("username");
        String password = json.getString("password");
        password = MD5.creatPassword(password);

        String bookmarkedAds;
        String lastAdsViewed;

        if (!json.isNull("bookmarkedAds")) {
            bookmarkedAds = json.getString("bookmarkedAds");
        } else {
            bookmarkedAds = "[]";
        }

        if (!json.isNull("lastAdsViewed")) {
            lastAdsViewed = json.getString("lastAdsViewed");
        } else {
            lastAdsViewed = "[]";
        }

        String query = "INSERT INTO Accounts (name, lastName, email, phoneNumber, city, username, password, " +
                "bookmarkedAds, lastAdsViewed, imagePath)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, phoneNumber);
        statement.setString(5, city);
        statement.setString(6, username);
        statement.setString(7, password);
        statement.setString(8, bookmarkedAds);
        statement.setString(9, lastAdsViewed);
        statement.setString(10, imagePath);

        statement.executeUpdate();

        connection.close();

        return true;
    }

    synchronized public static boolean updateAcc(JSONObject json) throws SQLException {
        boolean usernameChanged = true;
        int accID = json.getInt("accId");

        String update = "UPDATE Accounts SET name = ?, lastName = ?, username = ?, city = ?, password = ? WHERE accID = ?";

        Account account = Accounts.getUserByID(accID);
        String name = account.getName();
        String lastName = account.getLastName();
        String password = account.getPassword();
        String username = account.getUsername();
        String city = account.getCity();


        if (!json.getString("name").equals("")) {
            name = json.getString("name");
        }
        if (!json.getString("lastName").equals("")) {
            lastName = json.getString("lastName");
        }
        if (!json.getString("password").equals("")) {
            password = json.getString("password");
            password = MD5.creatPassword(password);
        }
        if (!json.getString("username").equals("")) {
            String temp = json.getString("username");
            if (!Accounts.usernameOrEmailExists(temp, "")) {
                username = temp;
            }
            else {
                usernameChanged = false;
            }
        }
        if (!json.getString("city").equals("")) {
            city = json.getString("city");
        }


        connection = DriverManager.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.setString(3, username);
        statement.setString(4, city);
        statement.setString(5, password);
        statement.setInt(6, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
        return usernameChanged;
    }

    synchronized public static Account getUserByID(int id) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT * FROM Accounts WHERE accID = " + id + ";";

        Statement statement = null;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String name = results.getString("name");
            String lastName = results.getString("lastName");
            String email = results.getString("email");
            String city = results.getString("city");
            String phoneNumber = results.getString("phoneNumber");
            String username = results.getString("username");
            String password = results.getString("password");
            int accID = results.getInt("accID");
            Gson gson = new Gson();
            String str = results.getString("lastAdsViewed");
            List<Integer> lastAdsViewed = gson.fromJson(str, List.class);
            str = results.getString("bookmarkedAds");
            List<Integer> bookmarkedAds = gson.fromJson(str, List.class);

            Account account = new Account(name, lastName, email, phoneNumber, city, username, password, lastAdsViewed, bookmarkedAds, accID);

            statement.close();
            results.close();
            connection.close();

            return account;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    synchronized public static Account getUserByUsername(String inputUsername) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT * FROM Accounts WHERE username = \"" + inputUsername + "\";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String name = results.getString("name");
            String lastName = results.getString("lastName");
            String email = results.getString("email");
            String city = results.getString("city");
            String phoneNumber = results.getString("phoneNumber");
            String username = results.getString("username");
            String password = results.getString("password");
            int accID = results.getInt("accID");
            Gson gson = new Gson();
            String str = results.getString("lastAdsViewed");
            List<Integer> lastAdsViewed = gson.fromJson(str, List.class);
            str = results.getString("bookmarkedAds");
            List<Integer> bookmarkedAds = gson.fromJson(str, List.class);
            boolean isOnline = results.getBoolean("online");

            Account account = new Account(name, lastName, email, phoneNumber, city, username, password, lastAdsViewed, bookmarkedAds, accID);

            connection.close();
            return account;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static Account getUserByEmail(String inputEmail) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT * FROM Accounts WHERE email = \"" + inputEmail + "\";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String name = results.getString("name");
            String lastName = results.getString("lastName");
            String email = results.getString("email");
            String city = results.getString("city");
            String phoneNumber = results.getString("phoneNumber");
            String username = results.getString("username");
            String password = results.getString("password");
            int accID = results.getInt("accID");
            Gson gson = new Gson();
            String str = results.getString("lastAdsViewed");
            List<Integer> lastAdsViewed = gson.fromJson(str, List.class);
            str = results.getString("bookmarkedAds");
            List<Integer> bookmarkedAds = gson.fromJson(str, List.class);
            boolean isOnline = results.getBoolean("online");

            Account account = new Account(name, lastName, email, phoneNumber, city, username, password, lastAdsViewed, bookmarkedAds, accID);

            connection.close();
            return account;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void deleteUser(int id) throws SQLException {
        connection = DriverManager.getConnection(url);
        try (PreparedStatement delete = connection.prepareStatement("DELETE FROM Ads WHERE accID = ?")) {
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

    synchronized public static boolean usernameMatchesPassword(String inputUsername, String inputPassword) throws SQLException {
        connection = DriverManager.getConnection(url);

        String query = "SELECT 1 FROM Accounts WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, inputUsername);
        ps.setString(2, inputPassword);
        ResultSet rs = ps.executeQuery();

        boolean result = rs.next();

        connection.close();
        return result;
    }

    synchronized public static boolean usernameOrEmailExists(String inputUsername, String inputEmail) throws SQLException {
        connection = DriverManager.getConnection(url);

        String query = "SELECT 1 FROM Accounts WHERE username = ? OR email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, inputUsername);
        ps.setString(2, inputEmail);
        ResultSet rs = ps.executeQuery();

        boolean result = rs.next();

        connection.close();
        return result;
    }

    synchronized public static void updateImagePath (int accID, String imagePath) throws SQLException {
        connection = DriverManager.getConnection(url);
        String update = "UPDATE Accounts SET imagePath = ? WHERE accID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        Gson gson = new Gson();
        statement.setString(1, imagePath);
        statement.setInt(2, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    synchronized public static String getUsersImagePath (int accID) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT imagePath FROM Accounts WHERE accID = " + accID + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            String imagePath = results.getString("imagePath");

            connection.close();
            return imagePath;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void updateLastViewedAds(int accID, List<Integer> history) throws SQLException {
        connection = DriverManager.getConnection(url);
        String update = "UPDATE Accounts SET LastAdsViewed = ? WHERE accID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        Gson gson = new Gson();
        statement.setString(1, gson.toJson(history));
        statement.setInt(2, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    synchronized public static void updateBookmarks(int accID, List<Integer> bookmarks) throws SQLException {
        connection = DriverManager.getConnection(url);
        String update = "UPDATE Accounts SET bookmarkedAds = ? WHERE accID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        Gson gson = new Gson();
        statement.setString(1, gson.toJson(bookmarks));
        statement.setInt(2, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    synchronized public static List<AD> getBookmarks(int accID) throws SQLException, ParseException {
        List<AD> results = new ArrayList<>();
        int[] IDs = getBookmarkIDs(accID);

        if (IDs == null)
            return results;

        for (Integer i : IDs) {
            System.out.println(i);
            results.add(DatabaseManager.getAdByID(i));
        }

        return results;
    }

    private static int[] toArray(String json, Gson parser) {
        return parser.fromJson(json, int[].class);
    }

    synchronized public static int[] getBookmarkIDs(int accID) throws SQLException, ParseException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT bookmarkedAds FROM Accounts WHERE accId = \"" + accID + "\";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            Gson gson = new Gson();
            String str = results.getString("bookmarkedAds");

            int[] bookmarkedAds = toArray(str, gson);
            System.out.println(Arrays.toString(bookmarkedAds));

            connection.close();

            return bookmarkedAds;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static List<AD> getHistory(int accID) throws SQLException, ParseException {
        List<AD> results = new ArrayList<>();
        int[] IDs = getHistoryIDs(accID);
        for (Integer i : IDs) {
            results.add(DatabaseManager.getAdByID(i));
        }
        return results;
    }

    synchronized public static int[] getHistoryIDs(int accID) throws SQLException, ParseException {
        connection = DriverManager.getConnection(url);

        String sql = "SELECT LastAdsViewed FROM Accounts WHERE accId = \"" + accID + "\";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            Gson gson = new Gson();
            String str = results.getString("LastAdsViewed");

            int[] LastAdsViewed = toArray(str, gson);
            System.out.println(Arrays.toString(LastAdsViewed));

            connection.close();

            return LastAdsViewed;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void statusChanger (int accID, boolean isOnline) throws SQLException {

        connection = DriverManager.getConnection(url);
        String update = "UPDATE Accounts SET online = ? WHERE accID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        statement.setBoolean(1, isOnline);
        statement.setInt(2, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    synchronized public static void isNumberAllowed (int accID, boolean isAllowed) throws SQLException {

        connection = DriverManager.getConnection(url);
        String update = "UPDATE Accounts SET showNum = ? WHERE accID = ?";


        PreparedStatement statement = connection.prepareStatement(update);
        statement.setBoolean(1, isAllowed);
        statement.setInt(2, accID);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

}
