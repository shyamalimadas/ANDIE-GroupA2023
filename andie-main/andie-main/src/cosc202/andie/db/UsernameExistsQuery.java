package cosc202.andie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * <p>
 * This class is used to check if a username exists in the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class UsernameExistsQuery {
    private DatabaseManager dbManager;
    private String username;

    /**
     * <p>
     * Construct a UsernameExistsQuery object.
     * </p>
     * 
     * <p>
     * The UsernameExistsQuery object is constructed with a DatabaseManager object
     * that it
     * will use to check if the username exists in the database.
     * </p>
     * 
     * @param dbManager     The DatabaseManager object to use for checking if the
     *                      username exists in
     *                      the database
     * @param loadingWindow The loading window to stop when the query is done
     * 
     * 
     */
    public UsernameExistsQuery(DatabaseManager dbManager, String username) {
        this.dbManager = dbManager;
        this.username = username;
    }

    /**
     * <p>
     * Check if the username exists in the database.
     * </p>
     * 
     * <p>
     * This method checks if the username exists in the database.
     * </p>
     * 
     * @return True if the username exists in the database, false otherwise
     */
    public Boolean check() {
        Connection connection = dbManager.getConnection();
        boolean usernameExists = false;

        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

            while (rs.next()) {
                usernameExists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usernameExists;
    }

}
