package DataBase;

import Account.Account;
import DataBase.Account.Accounts;
import DataBase.Chat.Chat;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws SQLException, ParseException {

        /*
        List<String> str = DatabaseManager.selectAds(0, 10);

        for (String s: str
        ) {
            System.out.println(s);
        }


        System.out.println("********************");


        DatabaseManager.checkingAdsInactivity();

        str = DatabaseManager.selectAds(0, 10);

        for (String s: str
        ) {
            System.out.println(s);
        }


        List<Integer> histoy = new ArrayList<>();
        histoy.add(7);
        histoy.add(9);
        histoy.add(11);


        Accounts.updateLastViewedAds(9, histoy);
        Accounts.updateBookmarks(1, histoy);
        Accounts.updateLastViewedAds(1, histoy);
        Accounts.updateBookmarks(9, histoy);


        Account account = Accounts.getUserByUsername("sarah");
        System.out.println(account.getName());
        System.out.println(account.getEmail());
        Gson gson = new Gson();

        System.out.println(gson.toJson(account));
        System.out.println("============");

        List<String> cats = new ArrayList<>();
        cats.add("BuildingsForSale");
        cats.add("Appliances");

        List<String> ads = DatabaseManager.selectAdsByCategoryAndPriceAndCity(cats, "tehran","0", "626200", 0, 10);
        for (String s :
                ads) {
            System.out.println(s);
        }
/*

         */
        //Accounts.statusChanger(1, true);

        System.out.println(Chat.countContacts(11));
        System.out.println();
        List<Integer> id = Chat.getAllContacts(11, 0, 10);
        List<Account> accounts = new ArrayList<>();

        for (int i : id) {
            accounts.add(Accounts.getUserByID(i));
        }

        for (Account a :
                accounts) {
            System.out.println(a.getDatabaseID());
        }
    }
}
