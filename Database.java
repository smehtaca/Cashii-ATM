import java.sql.*;

/**
 * Created by aumka on 3/27/2016.
 */
public class Database
{

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://firstfrontier.site.nfoservers.com:3306/firstfrontier_cashii";
    static final String USER = "firstfrontier";
    static final String PASS = "aAqVDxs4G3";

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
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");
            st = con.createStatement();
            System.out.println("Attempting to generate a result set...");
            ResultSet rs = st.executeQuery("SELECT AccountNum, UserPIN, LastName, FirstName, UserBalance");

        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }





    }
}

