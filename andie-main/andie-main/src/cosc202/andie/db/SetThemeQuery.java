package cosc202.andie.db;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.SwingWorker;

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
public class SetThemeQuery extends SwingWorker<Void, Void> {
    private DatabaseManager dbManager;
    private String username;
    private String theme;
    private LoadingWindow loadingWindow;

    /**
     * <p>
     * Construct a SetThemeQuery object.
     * </p>
     * 
     * <p>
     * The SetThemeQuery object is constructed with a DatabaseManager object that it
     * will use to get the theme from the database.
     * </p>
     * 
     * @param dbManager The DatabaseManager object to use for getting the theme from
     *                  the database
     * @param username  The username to get the theme for
     * 
     * @param theme     The theme to set
     * 
     */
    public SetThemeQuery(DatabaseManager dbManager, LoadingWindow loadingWindow, String username, String theme) {
        this.dbManager = dbManager;
        this.loadingWindow = loadingWindow;
        this.username = username;
        this.theme = theme;
    }

    /**
     * <p>
     * Get the theme from the database.
     * </p>
     * 
     * <p>
     * This method gets the theme from the database.
     * </p>
     * 
     * @return The theme from the database
     */
    @Override
    protected Void doInBackground() throws Exception {
        Connection connection = dbManager.getConnection();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("UPDATE userPreferences SET theme = '" + theme + "' WHERE username = '" + username + "'");

        return null;
    }

    /**
     * <p>
     * Stop the loading window.
     * </p>
     */
    @Override
    protected void done() {
        // nothing
    }

}
