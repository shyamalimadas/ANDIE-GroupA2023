package cosc202.andie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingWorker;

import cosc202.andie.Andie;
import cosc202.andie.LoadingWindow;

/**
 * <p>
 * This class is used to get the theme from the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class AddUserQuery extends SwingWorker<Boolean, Void> {
    private DatabaseManager dbManager;
    private String username;
    private String password;
    private LoadingWindow loadingWindow;

    /**
     * <p>
     * Construct a AddUserQuery object.
     * </p>
     * 
     * <p>
     * The AddUserQuery object is constructed with a DatabaseManager object that it
     * will use to add the user to the database.
     * </p>
     * 
     * @param dbManager     The DatabaseManager object to use for adding the user to
     *                      the database
     * @param loadingWindow The loading window to stop when the query is done
     * 
     * @param username      The username to add
     * 
     * @param password      The password to add
     * 
     */
    public AddUserQuery(DatabaseManager dbManager, LoadingWindow loadingWindow, String username, String password) {
        this.dbManager = dbManager;
        this.loadingWindow = loadingWindow;
        this.username = username;
        this.password = password;
    }

    /**
     * <p>
     * Add the user to the database.
     * </p>
     * 
     * <p>
     * This method adds the user to the database.
     * </p>
     * 
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        Connection connection = dbManager.getConnection();
        Statement stmt = connection.createStatement();

        boolean userAdded = false;

        try {

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

            while (rs.next()) {
                userAdded = false;
            }

            if (!userAdded) {
                stmt.executeUpdate(
                        "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
                stmt.executeUpdate("INSERT INTO userPreferences (theme, language, username) VALUES (NULL, NULL, '"
                        + username + "')");
                userAdded = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userAdded;
    }

    /**
     * <p>
     * Stop the loading window.
     * </p>
     */
    @Override
    protected void done() {
        loadingWindow.stop();

        try {
            Boolean userAdded = get();

            if (userAdded) {
                // Successful account creation
                System.out.println("Account created successfully!");
                Andie.setUser(username);

                try {
                    Andie.createLanguageGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // Failed account creation
                System.out.println("An error occurred while creating the account.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
