package cce_104Final;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database_connector {

    // Database credentials an+d URL
    private static final String AdminURL = "jdbc:mysql://localhost:3306/library_management"; 
    private static final String Username = "root"; // root ra >> mysql -h localhost -u [root]
    private static final String Pass = ""; // No Pssword ta leave blank ra

    // Method to establish a connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            // Establish the connection of the LocalHost
            connection = DriverManager.getConnection(AdminURL, Username, Pass);
            System.out.println("Connected to the database successfully!");

            //Notifies you if wa nimo gi on ang XAMPP!!
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
        return connection;
    }

    // Main method to test the connection
    public static void main(String[] args) {
        Connection conn = getConnection();
        
        if (conn != null) {
            try {
            	new library_system();

                // Close the connection when done
                conn.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}