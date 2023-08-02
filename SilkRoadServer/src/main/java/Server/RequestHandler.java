package Server;

import ADS.AD;
import Account.Account;
import DataBase.Account.Accounts;
import DataBase.Ads.*;
import DataBase.Ads.Buildings.BuildingsForSale;
import DataBase.Ads.Buildings.Lands;
import DataBase.Ads.Vehicle.BikesDB;
import DataBase.Ads.Vehicle.Cars;
import DataBase.Ads.Vehicle.MotorcyclesDB;
import DataBase.Chat.Chat;
import DataBase.DatabaseManager;
import DataBase.MD5;
import TextMessage.TextMessage;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class RequestHandler implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final Yandex mail = new Yandex();

    public RequestHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    synchronized public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = bufferedReader.readLine();
                message = new SilkRoadCipher().doDecrypt(message);
                JSONObject JsonRequest = new JSONObject(message);
                if (!JsonRequest.getString("Request").equals("getNotifications"))
                    System.out.println(JsonRequest);
                requestType(JsonRequest);

            } catch (IOException | SQLException | ParseException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private void requestType(JSONObject data) throws SQLException, ParseException, IOException {
        String Request = data.getString("Request");
        data.remove("Request");
        switch (Request) {

            case "finalSignUpAccount": {
                // register new client into database and if it was successful,
                boolean isSuccessful = Accounts.insertAccs(data);
                try {
                    if (isSuccessful) {
                        bufferedWriter.write("true");
                    } else {
                        bufferedWriter.write("false");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    System.out.println("result sent!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "checkForSignUp": {
                // this is going to check account's credentials and if data were ok, do finalsignup
                boolean isExist = Accounts.usernameOrEmailExists(data.getString("username"), data.getString("email"));
                // check database and return boolean
                try {
                    if (isExist) /*found in database */ {
                        System.out.println("Email/username" + "is already used in database.");
                        bufferedWriter.write("true");
                    } else {
                        bufferedWriter.write("false");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
                break;
            }

            case "emailVerificationCode": {
                boolean isEmailSent = mail.sendEmail("Verification", data.getString("email"));
                try {
                    if (isEmailSent) {
                        bufferedWriter.write("true");
                    } else {
                        bufferedWriter.write("false");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "checkEmailVerificationCode": {
                try {
                    System.out.println(mail.getCode());
                    if (mail.getCode().equals(data.getString("Code"))) {
                        bufferedWriter.write("true");
                    } else {
                        bufferedWriter.write("false");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "getMyAccount": {
                Account account = Accounts.getUserByUsername(data.getString("username"));

                Gson gson = new Gson();
                String result = gson.toJson(account);

                try {
                    bufferedWriter.write(result);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            }

            case "getMyAccountByEmail": {
                Account account = Accounts.getUserByEmail(data.getString("email"));

                Gson gson = new Gson();
                String result = gson.toJson(account);

                try {
                    bufferedWriter.write(result);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            }

            case "checkMyPassword": {
                // get password from database
                boolean userPassMatch = Accounts.usernameMatchesPassword(data.getString("username"), MD5.creatPassword(data.getString("password")));

                if (userPassMatch)
                    bufferedWriter.write("true");
                else
                    bufferedWriter.write("false");

                bufferedWriter.newLine();
                bufferedWriter.flush();
                break;
            }

            case "changeMyData": {
                // get data and check if they were not null,
                // then change into database
                // dont forget MD5
            }

            case "loginAccount": {
                // check data in database and return a boolean
                boolean userPassMatch = Accounts.usernameMatchesPassword(data.getString("username"), MD5.creatPassword(data.getString("password")));

                try {
                    if (userPassMatch)
                        bufferedWriter.write("true");
                    else
                        bufferedWriter.write("false");

                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
                break;
            }

            case "postNewAd": {
                int row = 0;
                switch (data.getString("Category")) {
                    case "digitalAccessories": {
                        row = AccessoriesDB.insertAccessories(data);
                        break;
                    }
                    case "appliances": {
                        row = AppliancesDB.insertAppliances(data);
                        break;
                    }
                    case "computer": {
                        row = ComputersDB.insertComputers(data);
                        break;
                    }
                    case "digitalAppliances": {
                        row = DigitalAppliancesDB.insertDigitalAppliances(data);
                        break;
                    }
                    case "gamingConsoles": {
                        row = GamingConsolesDB.insertGamingConsole(data);
                        break;
                    }
                    case "industrialEquipment": {
                        row = IndustrialEquipments.insertIndustrialEquipments(data);
                        break;
                    }
                    case "personalItem": {
                        row = PersonalItems.insertPersonalItem(data);
                        break;
                    }
                    case "phoneAndTablet": {
                        row = PhonesAndTabletsDB.insertPhoneAndTablets(data);
                        break;
                    }
                    case "car": {
                        row = Cars.insertCars(data);
                        break;
                    }
                    /*
                    case "buildingForRent": {
                        row = BuildingForRent.insertBuildingForRent(data);
                        break;
                    }

                     */
                    case "building": {
                        row = BuildingsForSale.insertBuildingForSale(data);
                        break;
                    }
                    case "land": {
                        row = Lands.insertLands(data);
                        break;
                    }
                    case "bike": {
                        System.out.println("inja");
                        row = BikesDB.insertBikes(data);
                        System.out.println("injaaaa");
                        break;
                    }
                    case "motorcycle": {
                        row = MotorcyclesDB.insertMotorcycles(data);
                        break;
                    }
                }


                bufferedWriter.write(String.valueOf(row));

                bufferedWriter.newLine();
                bufferedWriter.flush();

                break;
            }

            case "getHistory": {
                List<String> ads = new ArrayList<>();

                List<AD> history = Accounts.getHistory(data.getInt("accID"));

                Gson gson = new Gson();

                for (AD ad : history) {
                    ads.add(gson.toJson(ad));
                }

                System.out.println(ads.size());
                bufferedWriter.write(String.valueOf(ads.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : ads) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;
            }

            case "updateHistory": {
                int[] historyIDAr = Accounts.getHistoryIDs(data.getInt("accID"));
                int newAdID = data.getInt("adID");

                List<Integer> historyIDs = new ArrayList<>();
                for (int i : historyIDAr) {
                    historyIDs.add(i);
                }

                if (!historyIDs.contains(newAdID)) {
                    historyIDs.add(newAdID);
                } else {
                    int index = historyIDs.indexOf(newAdID);
                    historyIDs.remove(index);
                    historyIDs.add(newAdID);
                }

                while (historyIDs.size() > 8) {
                    historyIDs.remove(0);
                }

                Accounts.updateLastViewedAds(data.getInt("accID"), historyIDs);

                break;
            }

            case "getBookmark": {
                List<AD> bookmarks = Accounts.getBookmarks(data.getInt("accID"));
                List<String> ads = new ArrayList<>();

                Gson gson = new Gson();

                for (AD ad : bookmarks) {
                    ads.add(gson.toJson(ad));
                }

                int totalSize = Accounts.getBookmarkIDs(data.getInt("accID")).length;

                String tSize = String.valueOf(totalSize);
                bufferedWriter.write(tSize);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                System.out.println(ads.size());
                bufferedWriter.write(String.valueOf(ads.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : ads) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;
            }

            case "updateBookmarks": {
                int[] bookmarkIDsAr = Accounts.getBookmarkIDs(data.getInt("accID"));
                int newAdID = data.getInt("adID");
                List<Integer> bookmarkIDs = new ArrayList<>();

                for (int i : bookmarkIDsAr) {
                    bookmarkIDs.add(i);
                }

                if (!bookmarkIDs.contains(newAdID)) {
                    bookmarkIDs.add(newAdID);
                } else {
                    int index = bookmarkIDs.indexOf(newAdID);
                    bookmarkIDs.remove(index);
                }

                Accounts.updateBookmarks(data.getInt("accID"), bookmarkIDs);

                break;
            }

            case "getMyAds": {
                List<String> ads = DatabaseManager.getUsersADs(data.getInt("accID"), data.getInt("from"),
                        data.getInt("to"));

                int totalSize = DatabaseManager.countUsersAd(data.getInt("accID"));

                String tSize = String.valueOf(totalSize);
                System.out.println(tSize);
                bufferedWriter.write(tSize);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String size = String.valueOf(ads.size());
                System.out.println(size);
                bufferedWriter.write(size);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : ads) {
                    System.out.println(s);
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;
            }

            case "refreshTimeLine": {

                break;
            }

            case "getSpecificAd": {

                int adId = data.getInt("adID");
                try {
                    AD ad;
                    ad = DatabaseManager.getAdByID(adId);
                    int i = String.valueOf(ad.getClass()).lastIndexOf('.');
                    String category = "";
                    if (i > 0) {
                        category = String.valueOf(ad.getClass()).substring(i + 1);
                    }
                    System.out.println(category);

                    bufferedWriter.write(category);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    Gson gson = new Gson();
                    String AD = gson.toJson(ad);
                    bufferedWriter.write(AD);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (ParseException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }

                break;
            }

            case "sendMessage": {
                Chat.insertChat(String.valueOf(data));
                break;
            }

            case "getMyMessages": {
                // find all messages of same sender and receiver
                // and return them 10 by 10
                // data includes senderUsername, receiverUsername
                List<String> chats = Chat.readAChat(data.getInt("firstUserID"), data.getInt("secondUserID"));

                for (String s : chats) {
                    System.out.println(s);
                }

                System.out.println(chats.size());
                bufferedWriter.write(String.valueOf(chats.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : chats) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                break;
            }

            case "loadNewMessages": {
                List<String> chats = Chat.readNewMessages(data.getInt("receiverID"), data.getInt("senderID"));

                for (String s : chats) {
                    System.out.println(s);
                }

                System.out.println(chats.size());
                bufferedWriter.write(String.valueOf(chats.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : chats) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                break;
            }

            case "search": {
                List<String> ads = new ArrayList<>();
                List<String> categories = new ArrayList<>();

                JSONArray ja = data.getJSONArray("category"); // get the JSONArray

                for (int i = 0; i < ja.length(); i++) {
                    categories.add(ja.getString(i)); // iterate the JSONArray and extract the keys
                }

                System.out.println(categories);

                String lowerBound = null;
                String upperBound = null;
                String city = null;
                String title = null;

                if (data.keySet().contains("lowerBound")) {
                    lowerBound = data.getString("lowerBound");
                }
                if (data.keySet().contains("upperBound"))
                    upperBound = data.getString("upperBound");
                if (data.keySet().contains("city"))
                    city = data.getString("city");
                if (data.keySet().contains("title"))
                    title = data.getString("title");


                System.out.println("city " + city);

                int from = data.getInt("from");
                int to = data.getInt("to");

                ads = DatabaseManager.selectAdsByCategoryAndCityAndPriceAndTitle(categories, city, lowerBound, upperBound, title, from, to);

                int totalSize = DatabaseManager.countSearchResults(categories, city, lowerBound, upperBound, title);

                System.out.println("totalSize: " + totalSize);
                String tSize = String.valueOf(totalSize);
                bufferedWriter.write(tSize);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                System.out.println(String.valueOf(ads.size()));
                bufferedWriter.write(String.valueOf(ads.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : ads) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;
            }

            case "saveUserPicture": {

                String path = ImageHandler.receiveUserImage(socket);
                System.out.println(data.getInt("accID"));
                Accounts.updateImagePath(data.getInt("accID"), path);

                break;
            }

            case "saveAdPicture": {

                String path = ImageHandler.receiveADImage(socket);
                System.out.println("path of received file: " + path);
                DatabaseManager.updateImagePath(data.getInt("adID"), path);

                break;
            }

            case "getUserPicture": {
                int accID = data.getInt("accID");
                ImageHandler imageHandler = new ImageHandler(accID, true);
                imageHandler.sendImage(socket);

                break;
            }

            case "getAdPicture": {
                int adID = data.getInt("adID");
                ImageHandler imageHandler = new ImageHandler(adID, false);
                imageHandler.sendImage(socket);

                break;
            }

            case "getNotifications": {
                List<TextMessage> textMessages = Chat.getNotifications(data.getInt("receiverID"));

                Gson gson = new Gson();
                List<String> toClient = new ArrayList<>();

                for (TextMessage t : textMessages) {
                    toClient.add(gson.toJson(t));
                }

                // System.out.println(toClient.size());
                bufferedWriter.write(String.valueOf(toClient.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : toClient) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;

            }

            case "getContacts": {
                List<Integer> accountsID = Chat.getAllContacts(data.getInt("accID"), data.getInt("from"),
                        data.getInt("to"));

                int totalSize = Chat.countContacts(data.getInt("accID"));
                System.out.println(totalSize);
                bufferedWriter.write(String.valueOf(totalSize));
                bufferedWriter.newLine();
                bufferedWriter.flush();


                Gson gson = new Gson();
                List<String> accounts = new ArrayList<>();
                for (int i : accountsID) {
                    accounts.add(gson.toJson(Accounts.getUserByID(i)));
                }

                System.out.println(accounts.size());
                bufferedWriter.write(String.valueOf(accounts.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : accounts) {
                    System.out.println(s);
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;

            }

            case "updateInfo" : {
                boolean usernameChanged = Accounts.updateAcc(data);
                // the only case where we can not edit info is when the username is already taken
                // all other info is certainly changed
                if (usernameChanged)
                    bufferedWriter.write("true");
                else
                    bufferedWriter.write("false");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                break;
            }

            case "getSuggestion": {
                int lastID;

                int[] history = Accounts.getHistoryIDs(data.getInt("accID"));

                String category1 = null;
                if (history.length > 0) {
                    lastID = history[history.length - 1];
                    category1 = DatabaseManager.getAdsCategory(lastID);
                }

                int[] bookmarks = Accounts.getBookmarkIDs(data.getInt("accID"));

                String category2 = null;
                if (bookmarks.length > 0) {
                    lastID = bookmarks[bookmarks.length - 1];
                    category2 = DatabaseManager.getAdsCategory(lastID);
                }

                List<String> cats = new ArrayList<>();
                cats.add(category1);
                cats.add(category2);

                List<String> suggested = DatabaseManager.selectAdsByCategoryAndCityAndPriceAndTitle(cats, null, null,
                        null, null, 0, 5);


                /*
                int totalSize = DatabaseManager.countSearchResults(cats, null, null,
                        null, null);

                System.out.println(totalSize);
                bufferedWriter.write(String.valueOf(totalSize));
                bufferedWriter.newLine();
                bufferedWriter.flush();
                 */

                System.out.println(suggested.size());
                bufferedWriter.write(String.valueOf(suggested.size()));
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (String s : suggested) {
                    System.out.println(s);
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                break;

            }

            case "passwordRecovery": {
                mail.sendEmail("Recovery", data.getString("email"));

                bufferedWriter.write(mail.getCode());
                bufferedWriter.newLine();
                bufferedWriter.flush();

                break;
            }
        }
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
