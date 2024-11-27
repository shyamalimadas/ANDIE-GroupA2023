package cosc202.andie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import cosc202.andie.Andie;
import cosc202.andie.LoadingWindow;
import cosc202.andie.Themes;

/**
 * <p>
 * This class is used to get the theme from the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class GetThemeQuery extends SwingWorker<String, Void> {
    private DatabaseManager dbManager;
    private String username;
    private LoadingWindow loadingWindow;

    /**
     * <p>
     * Construct a GetThemeQuery object.
     * </p>
     * 
     * <p>
     * The GetThemeQuery object is constructed with a DatabaseManager object that it
     * will use to get the theme from the database.
     * </p>
     * 
     * @param dbManager The DatabaseManager object to use for getting the theme from
     *                  the database
     * @param username  The username to get the theme for
     * 
     * @param loadingWindow The loading window to stop when the query is done
     * 
     */
    public GetThemeQuery(DatabaseManager dbManager, LoadingWindow loadingWindow, String username) {
        this.dbManager = dbManager;
        this.username = username;
        this.loadingWindow = loadingWindow;
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
    protected String doInBackground() throws Exception {
        Connection connection = dbManager.getConnection();
        String theme = "";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM userPreferences WHERE username = '" + username + "'");

        if (rs.next()) {
            theme = rs.getString("theme");
        }

        System.out.println("\n\n!!!!!!!!!!!!!\n\nTheme returned from database: " + theme + "\n\n!!!!!!!!!!!!\n\n");
        return theme;
    }

    /**
     * <p>
     * Stop the loading window.
     * </p>
     */
    @Override
    protected void done() {
        try {
            String theme = get();

            if (theme == null || theme.isEmpty()) {
                System.out.println("theme changed to default");
                theme = "Default";
            }

            System.out.println("Andie theme set to: " + theme);
            Andie.setTheme(theme);
            //Themes.applyTheme(theme);
            // SwingUtilities.updateComponentTreeUI(Andie.frame);

        } catch (Exception e) {
            e.printStackTrace();
        } 

        GetLanguageQuery getLanguageQuery = new GetLanguageQuery(dbManager, loadingWindow, username);
        getLanguageQuery.execute();
    }

}
