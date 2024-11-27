package cosc202.andie.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private String hostname = "baoonragwrzg9rs1hbv6-mysql.services.clever-cloud.com";
    private String port = "21544";
    private String database = "baoonragwrzg9rs1hbv6";
    private String dbUsername = "uckcwx3ebmbp4hqz";
    private String dbPassword = "qe0wEJW7oeWvuGMWKw91";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?connectTimeout=3000";

    /**
     * <p>
     * Construct a DatabaseManager object.
     * </p>
     * 
     * <p>
     * The DatabaseManager object initialises the connection to the database.
     * </p>
     * 
     */
    public DatabaseManager() {
        try {
            LoadDriver();

            try {
                System.out.println("connecting.....");
                connection = DriverManager.getConnection(url, dbUsername, dbPassword);
                System.out.println("Connected to database");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            quit(e.getMessage());
        }
    }

    /**
     * <p>
     * Method that returns the connection.
     * </p>
     * 
     * @return The connection to the database.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * <p>
     * Method that closes the connection.
     * </p>
     */
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            quit(e.getMessage());
        }
    }

    /**
     * <p>
     * Method that quits the program.
     * </p>
     * 
     * @param message The message to print before quitting.
     */
    private void quit(String message) {
        System.err.println(message);
        System.exit(1);
    }

    /**
     * <p>
     * Method that loads the driver.
     * </p>
     */
    private void LoadDriver() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (Exception e){
       	   System.out.println(e.getMessage());
       	   System.exit(1);
       }
    }
}

