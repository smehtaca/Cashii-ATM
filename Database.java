import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by aumka on 3/27/2016.
 */
public class Database {
    // Driver info
    static final String DRIVER = "com.mysql.jdbc.Driver";

    // SQL Database info (hosted externally)
    static final String DB_URL = "jdbc:mysql://firstfrontier.site.nfoservers.com:3306/firstfrontier_cashii";
    static final String USER = "firstfrontier";
    static final String PASS = "aAqVDxs4G3";

    int accountNum, accountPIN;
    String firstName, lastName;
    double accountBalance;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    public Database() {
        connect();
    }

    void connect() {


        try {
            Class.forName("com.mysql.jdbc.Driver"); // Driver is located in src/mysql-connector...
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Connecting to database...");

        try {
            // Make the connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection established!");
            System.out.println("Creating statement...");

            // Create a statement (not for output)
            st = con.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deposit(int id, int amount, int accountType) {
        int idCompare; // will contain the ID needed
        String account;

        // If the accountType is 0, then it's a Chequing account the user wants to deposit to, if it's 1 then
        // it's savings
        account = accountType == 0 ? "UserBalanceC" : "UserBalanceS";

        // Look into the account number and user balance for the deposit
        String sql = "SELECT AccountNum, " + account +" FROM CashiiDB2";

        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                idCompare = rs.getInt("AccountNum"); // grab the id to compare with after

                // If the ID turns about to be the one that's needed, get the balance and add the amount needed
                if (idCompare == id) {
                    accountBalance = rs.getDouble(account);
                    accountBalance += amount;
                }
            }

            // DEBUG: What is it modifying?
            System.out.print("ID: " + id);
            System.out.println(", Balance: " + accountBalance);

            // Run the operation to update the balance only for the user's account
            sql = "UPDATE CashiiDB2 " + "SET "+ account +" ='" + accountBalance + "' WHERE AccountNum in ('" + id + "')";
            st.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        accountBalance = 0; // clean up after messing with global vars
    }
}

