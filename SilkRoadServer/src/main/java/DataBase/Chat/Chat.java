package DataBase.Chat;

import DataBase.Account.Accounts;
import TextMessage.TextMessage;
import com.google.gson.Gson;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat
{
    private final static String url = "jdbc:sqlite:AllAds.db";
    private static Connection connection;
    private static String time;
    static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    private static void setTime() {
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
    }

    synchronized public static void insertChat (String json) throws SQLException {
        setTime();
        Date date = new Date(System.currentTimeMillis());
        connection = DriverManager.getConnection(url);
        String sql = "INSERT INTO textMessages (senderID, receiverID, content, date, delivered, notification) VALUES (?, ?, ?, ?, ?, ?)";
        Gson gson = new Gson();
        TextMessage textMessage = gson.fromJson(json, TextMessage.class);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, textMessage.getSenderID());
            statement.setInt(2, textMessage.getReceiverID());
            statement.setString(3, textMessage.getContent());
            statement.setString(4, time);
            statement.setString(5, "false");
            statement.setString(6, "false");

            statement.executeUpdate();
            connection.close();
        }catch (SQLException e)
        {
            e.getMessage();
        }
    }

    synchronized public static List<String> readAChat (int firstUserID, int secondUserID) throws ParseException, SQLException {
        connection = DriverManager.getConnection(url);
        List<TextMessage> texts = new ArrayList<>();
        List<String> result = new ArrayList<>();

        String sql = "SELECT * FROM textMessages WHERE (senderID = ? AND receiverID = ?) OR (senderID = ? AND receiverID = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, firstUserID);
            statement.setInt(2, secondUserID);
            statement.setInt(3, secondUserID);
            statement.setInt(4, firstUserID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt(1);
                int senderID = resultSet.getInt(2);
                int receiverID = resultSet.getInt(3);
                String content = resultSet.getString(4);
                Date date = formatter.parse(resultSet.getString(5));

                TextMessage textMessage = new TextMessage(id, senderID, receiverID, content, date);
                Gson gson = new Gson();
                String text = gson.toJson(textMessage);

                texts.add(textMessage);
                result.add(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        connection.close();

        for (TextMessage t: texts) {
            markMessageAsDelivered(t);
        }

        return result;
    }

    synchronized public static List<String> readNewMessages (int senderID, int receiverID) throws ParseException, SQLException {
        connection = DriverManager.getConnection(url);
        List<TextMessage> texts = new ArrayList<>();
        List<String> result = new ArrayList<>();

        String sql = "SELECT * FROM textMessages WHERE (senderID = ? AND receiverID = ? AND delivered = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, senderID);
            statement.setInt(2, receiverID);
            statement.setString(3, "false");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt(1);
                int sender = resultSet.getInt(2);
                int receiver = resultSet.getInt(3);
                String content = resultSet.getString(4);
                Date date = formatter.parse(resultSet.getString(5));

                TextMessage textMessage = new TextMessage(id, senderID, receiverID, content, date);
                Gson gson = new Gson();

                texts.add(textMessage);
                result.add(gson.toJson(textMessage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();

        for (TextMessage t: texts) {
            markMessageAsDelivered(t);
        }

        return result;
    }

    synchronized private static void markMessageAsDelivered (TextMessage textMessage) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "UPDATE textMessages SET delivered = ? WHERE textID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "true");
            preparedStatement.setInt(2, textMessage.getTextID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();
    }

    synchronized public static List<TextMessage> getNotifications (int receiverID) throws SQLException, ParseException {
        connection = DriverManager.getConnection(url);
        List<TextMessage> textMessages = new ArrayList<>();
        String sql = "SELECT * FROM textMessages WHERE receiverID = " + receiverID +
                " AND delivered != \"true\" AND notification != \"true\" ";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next())
        {
            int textID = result.getInt("textID");
            int senderID = result.getInt("senderID");
            String content = result.getString("content");
            String temp = result.getString("date");
            Date date = formatter.parse(temp);

            String senderUsername = Accounts.getUserByID(senderID).getUsername();

            TextMessage textMessage = new TextMessage(textID, content, date, senderUsername);

            textMessages.add(textMessage);
        }

        connection.close();

        for (TextMessage t: textMessages) {
            makeNotificationTrue(t);
        }
        return textMessages;
    }



    synchronized private static void makeNotificationTrue (TextMessage textMessage) throws SQLException {
        connection = DriverManager.getConnection(url);

        String sql = "UPDATE textMessages SET notification = ? WHERE textID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "true");
            preparedStatement.setInt(2, textMessage.getTextID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();
    }


    synchronized public static List<Integer> getAllContacts (int userID, int from, int length) throws ParseException, SQLException {
        setTime();
        connection = DriverManager.getConnection(url);
        List<Integer> result = new ArrayList<>();

        String sql = "SELECT DISTINCT senderID, receiverID FROM (SELECT * FROM textMessages WHERE senderID = ? OR " +
                "receiverID = ?) s LIMIT " + from + ", " + length +
                ";";
        System.out.println(sql);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userID);
            statement.setInt(2, userID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int senderID = resultSet.getInt(1);
                int receiverID = resultSet.getInt(2);

                if (senderID == userID && !result.contains(receiverID))
                    result.add(receiverID);
                else if (receiverID == userID && !result.contains(senderID)) {
                    result.add(senderID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();
        System.out.println(result);
        return result;
    }

    synchronized public static int countContacts (int userID) throws ParseException, SQLException {
        connection = DriverManager.getConnection(url);
        List<Integer> contacts = new ArrayList<>();

        String sql = "SELECT senderID, receiverID FROM (SELECT * FROM TextMessages WHERE (senderID = ?) OR " +
                "(receiverID = ?)) ;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userID);
            statement.setInt(2, userID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int senderID = resultSet.getInt(1);
                int receiverID = resultSet.getInt(2);

                if (senderID == userID && !contacts.contains(receiverID))
                    contacts.add(receiverID);
                else if (receiverID == userID && !contacts.contains(senderID)) {
                    contacts.add(senderID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();
        return contacts.size();
    }

}
