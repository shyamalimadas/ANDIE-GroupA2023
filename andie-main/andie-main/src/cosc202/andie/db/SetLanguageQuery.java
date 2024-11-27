package cosc202.andie.db;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.SwingWorker;

import cosc202.andie.LoadingWindow;

/**
 * <p>
 * This class is used to get the language from the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class SetLanguageQuery extends SwingWorker<Void, Void> {
    private DatabaseManager dbManager;
    private String username;
    private String language;
    private LoadingWindow loadingWindow;

    /**
     * <p>
     * Construct a SetLanguageQuery object.
     * </p>
     * 
     * <p>
     * The SetLanguageQuery object is constructed with a DatabaseManager object that
     * it
     * will use to get the language from the database.
     * </p>
     * 
     * @param dbManager The DatabaseManager object to use for getting the language
     *                  from
     *                  the database
     * @param username  The username to get the language for
     * 
     * @param language  The language to set
     * 
     */
    public SetLanguageQuery(DatabaseManager dbManager, LoadingWindow loadingWindow, String username, String language) {
        this.dbManager = dbManager;
        this.loadingWindow = loadingWindow;
        this.username = username;
        this.language = language;
    }

    /**
     * <p>
     * Get the language from the database.
     * </p>
     * 
     * <p>
     * This method gets the language from the database.
     * </p>
     * 
     */
    @Override
    protected Void doInBackground() throws Exception {
        Connection connection = dbManager.getConnection();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate(
                "UPDATE userPreferences SET language = '" + language + "' WHERE username = '" + username + "'");

        return null;
    }

    /**
     * <p>
     * Stop the loading window.
     * </p>
     */
    @Override
    protected void done() {
        loadingWindow.stop();
    }

}
