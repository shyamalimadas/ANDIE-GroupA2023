package cosc202.andie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import cosc202.andie.Andie;
import cosc202.andie.LoadingWindow;
import cosc202.andie.Themes;

/**
 * <p>
 * This class is used to get the language from the database.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class GetLanguageQuery extends SwingWorker<String, Void> {
    private DatabaseManager dbManager;
    private String username;
    private LoadingWindow loadingWindow;
    protected static ResourceBundle bundle;

    /**
     * <p>
     * Construct a GetLanguageQuery object.
     * </p>
     * 
     * <p>
     * The GetLanguageQuery object is constructed with a DatabaseManager object that
     * it
     * will use to get the language from the database.
     * </p>
     * 
     * @param dbManager The DatabaseManager object to use for getting the language
     *                  from
     *                  the database
     * @param username  The username to get the language for
     * 
     */
    public GetLanguageQuery(DatabaseManager dbManager, LoadingWindow loadingWindow, String username) {
        this.dbManager = dbManager;
        this.loadingWindow = loadingWindow;
        this.username = username;
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
     * @return The language from the database
     */
    @Override
    protected String doInBackground() throws Exception {
        Connection connection = dbManager.getConnection();
        String language = "";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM userPreferences WHERE username = '" + username + "'");

        while (rs.next()) {
            language = rs.getString("language");
        }

        return language;
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
            String language = get();

            if (language == null || language.isEmpty()) {

                Andie.createLanguageGUI();

            } else {
                setResourceBundle(language);
                Andie.setResourceBundle(bundle);
                //Themes.applyTheme("dark");


                try{
                Andie.createAndShowGUI();
                SwingUtilities.updateComponentTreeUI(Andie.frame);
                Andie.frame.pack();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            loadingWindow.stop();

        }
    }

    /**
     * <p>
     * Set the resource bundle.
     * </p>
     * 
     * <p>
     * This method sets the resource bundle based on the language.
     * </p>
     * 
     * @param language The language to set the resource bundle for
     */
    public static void setResourceBundle(String language) {

        if (language == null) {
            Locale locale = new Locale("en", "NZ");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("English (NZ)")) {
            Locale locale = new Locale("en", "NZ");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Te Reo Māori")) {
            Locale locale = new Locale("mi", "NZ");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Русский")) {
            Locale locale = new Locale("ru", "RU");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Español")) {
            Locale locale = new Locale("es", "ES");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Deutsch")) {
            Locale locale = new Locale("de", "DE");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Français")) {
            Locale locale = new Locale("fr", "FR");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        } else if (language.equals("Japanese")) {
            Locale locale = new Locale("ja", "JP");
            bundle = ResourceBundle.getBundle("MessageBundle", locale);
        }

    }

}
