import java.sql.*;

/**
 * Created by aumka on 3/27/2016.
 */
public class Database
{
    // Driver info
    static final String DRIVER = "com.mysql.jdbc.Driver";

    // SQL Database info (hosted externally)
    static final String DB_URL = "jdbc:mysql://firstfrontier.site.nfoservers.com:3306/firstfrontier_cashii";
    static final String USER = "firstfrontier";
    static final String PASS = "aAqVDxs4G3";

    int accountNum, accountPIN;
    String firstName, lastName;
    double accountBalance;

    public Database()
    {
    connect();
    }

    public void connect(){
    Connection con = null;
    Statement st = null;

        try {
            Class.forName("com.mysql.jdbc.Driver"); // Driver is located in src/mysql-connector...
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Connecting to database...");

        try {
            // Make the connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");

            // Create a statement (not for output)
            st = con.createStatement();

            // Make a result set to be read through (contains all the account info)
            System.out.println("Attempting to generate a result set...");
            ResultSet rs = st.executeQuery("SELECT AccountNum, UserPIN, LastName, FirstName, UserBalance FROM CashiiDB");

            // Go through the result set and grab all columns
            while (rs.next())
            {
                accountNum = rs.getInt("AccountNum");
                accountPIN = rs.getInt("UserPIN");
                lastName = rs.getString("LastName");
                firstName = rs.getString("FirstName");
                accountBalance = rs.getDouble("UserBalance");

                // DEBUG: Gets user info, however much there is.
                System.out.print("Account Number: " +accountNum);
                System.out.print(", User PIN: " +accountPIN);
                System.out.print(", Name: " +firstName + " " +lastName);
                System.out.println(", Balance: " +accountBalance);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

