package cosc202.andie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * <p>
 * This class is used to get the password from the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class PasswordQuery {
    private DatabaseManager dbManager;
    private String username;

    /**
     * <p>
     * Construct a PasswordQuery object.
     * </p>
     * 
     * <p>
     * The PasswordQuery object is constructed with a DatabaseManager object that it
     * will use to get the user's password from the database.
     * </p>
     * 
     * @param dbManager     The DatabaseManager object to use for getting the
     *                      password from
     *                      the database
     * @param username      The username to get the password for
     * 
     * @param loadingWindow The loading window to stop when the query is done
     * 
     */
    public PasswordQuery(DatabaseManager dbManager, String username) {
        this.dbManager = dbManager;
        this.username = username;
    }

    /**
     * <p>
     * Get the password from the database.
     * </p>
     * 
     * <p>
     * This method gets the password from the database.
     * </p>
     * 
     * @return The password from the database
     */
    public String getPassword() {
        Connection connection = dbManager.getConnection();
        String password = "";

        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

            while (rs.next()) {
                password = rs.getString("password");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return password;
    }

}
