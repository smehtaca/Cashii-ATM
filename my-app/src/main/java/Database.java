import java.sql.*;

/**
 * This class is responsible for communicating with the SQL database
 * and making updates to it as well as retreiving from it. The SparkJava
 * API uses these functions.
 */

public class Database {
    // Driver info
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static String sql = " ";

    // SQL Database info (hosted externally)
    static final String DB_URL = "jdbc:mysql://firstfrontier.site.nfoservers.com:3306/firstfrontier_cashii";
    static final String USER = "firstfrontier";
    static final String PASS = "aAqVDxs4G3";

    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;

    public Database() {
        // Connect to the database when an object is created.
        connect();
    }

    /**
     * Connects to the database.
     */
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

    /**
     * Deposits into an account of the user's choice
     *
     * @param id          holds the user's account number
     * @param amount      holds the amount that is trying to be withdrawn
     * @param accountType holds the account it's trying to withdraw from
     */
    int deposit(int id, int amount, int accountType) {
        int idCompare; // will contain the ID needed
        double accountBalance = 0;
        String account; // 0 = chequing, 1 = savings

        // If the accountType is 0, then it's a Chequing account the user wants to deposit to, if it's 1 then
        // it's savings
        account = accountType == 0 ? "UserBalanceC" : "UserBalanceS";

        // Look into the account number and user balance for the deposit
        String sql = "SELECT AccountNum, " + account + " FROM CashiiDB2";

        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                idCompare = rs.getInt("AccountNum"); // grab the id to compare with after

                // If the ID turns about to be the one that's needed, get the balance and add the amount needed
                if (idCompare == id) {
                    accountBalance = rs.getDouble(account);
                    accountBalance += amount;
                    break;
                } else
                    return -1;
            }
            // Run the operation to update the balance only for the user's account
            updateAccount(id, accountBalance, account);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        System.out.println("AMOUNT: " + accountBalance);
        return 1;
    }

    /**
     * Withdraws from the account after querying the database.
     *
     * @param id          holds the user's account number
     * @param amount      holds the amount that is trying to be withdrawn
     * @param accountType holds the account it's trying to withdraw from
     * @return -1 if the user is trying to withdraw too much, 1 if the amount has been successfully withdrawn
     */
    int withdraw(int id, int amount, int accountType) {
        int idCompare; // will contain the ID needed
        double accountBalance = 0;
        String account; // 0 = chequing, 1 = savings

        // If the accountType is 0, then it's a Chequing account the user wants to deposit to, if it's 1 then
        // it's savings
        account = accountType == 0 ? "UserBalanceC" : "UserBalanceS";

        // Look into the account number and user balance for the deposit
        String sql = "SELECT AccountNum, " + account + " FROM CashiiDB2";

        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                idCompare = rs.getInt("AccountNum"); // grab the id to compare with after

                // If the ID turns about to be the one that's needed, get the balance and add the amount needed
                if (idCompare == id) {
                    accountBalance = rs.getDouble(account);
                    if (accountBalance <= amount)
                        return -1;
                    else {
                        accountBalance -= amount;
                    }
                }
            }
            updateAccount(id, accountBalance, account); // update command
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * Updates either balance (chequing or savings) of the account.
     *
     * @param id             holds the user's account number.
     * @param accountBalance contains the new balance
     * @param account        contains the account type that is to be changed
     */
    void updateAccount(int id, double accountBalance, String account) {
        // Update only the required keys
        sql = "UPDATE CashiiDB2 " + "SET " + account + " ='" + accountBalance + "' WHERE AccountNum in ('" + id + "')";
        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticate the user.
     *
     * @param accountNum is inputted by the user
     * @param accountPIN is inputted by the user as well
     * @return 1 if successfully authenticated, -1 if it can't find anything or info is invalid.
     */
    static int auth(int accountNum, int accountPIN) {
        int accountCompare, PINCompare; // need to compare user and password

        // Select what is needed from the DB
        sql = "SELECT AccountNum, UserPIN FROM CashiiDB2";

        try {

            // Run the select query
            rs = st.executeQuery(sql);

            // Keep comparing till it finds what it needs
            while (rs.next()) {
                accountCompare = rs.getInt("AccountNum");
                PINCompare = rs.getInt("UserPIN");

                if (accountNum == accountCompare && accountPIN == PINCompare) {
                    System.out.println("User authentication successful!");
                    return 1;
                } // it found the pair, it's authed
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User authentication unsuccessful."); // Debug dialogue
        return -1; // it couldn't find anything, not authed
    }

    /**
     * Returns an account statement to the user.
     *
     * @param accountNum so the SQL API knows what to look for
     * @return HTML formatted output of the account statement
     */
    static String getAccountStatement(int accountNum) {
        int accountCompare; // for comparison
        try {
            // Select all the SQL columns needed to output a statement
            rs = st.executeQuery("SELECT AccountNum, LastName, FirstName, UserBalanceC, UserBalanceS FROM CashiiDB2");

            // Keep comparing till the if statement finds what it needs, then output
            while (rs.next()) {
                accountCompare = rs.getInt("AccountNum");
                if (accountCompare == accountNum)
                    return "<h1>" + rs.getString("FirstName") + " " + rs.getString("LastName") + "</h1>" + "Account Number: " + rs.getInt("AccountNum") + "<br> <p>Savings: $" + rs.getInt("UserBalanceS") + "<br>Chequing: $" + rs.getInt("UserBalanceC") + "<p>";
                // accountCompare + ", " + rs.getInt("UserPIN") + ", \n" + rs.getString("LastName") + ", " + rs.getString("FirstName") + ", " + rs.getDouble("UserBalanceC") + ", " +rs.getDouble("UserBalanceS");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return "NOTFOUND"; // This should not happen in any circumstance
    }
}

