package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import java.util.function.Consumer;
import javax.swing.plaf.metal.MetalLookAndFeel;

import cosc202.andie.db.*;

import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * <p>
 * Themes to be used in the application.
 * </p>
 * 
 * <p>
 * Menu of themes to choose from and apply to the application.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 * 
 */
public class Themes {

    private static Preferences userPrefs = Preferences.userNodeForPackage(Themes.class);
    private static JFrame frame;

    protected static LoadingWindow loadingWindow;

    /**
     * <p>
     * Apply the theme to the application.
     * </p>
     * 
     * @param onThemeChanged The theme to apply.
     */
    public static void showThemeSelector(Consumer<String> onThemeChanged) {
        String theme = chooseTheme();
        applyTheme(theme);

        frame = new JFrame("Theme Selector");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(300, 200);

        //positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);

        String[] themes = { "default", "dark", "nimbus", "metal", "motif", "windows", "gtk",
                "macos", "system" };
        JComboBox<String> themeSelector = new JComboBox<>(themes);
        themeSelector.setSelectedItem(theme);

        themeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheme = (String) themeSelector.getSelectedItem();
                applyTheme(selectedTheme);
                onThemeChanged.accept(selectedTheme);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                userPrefs.put("theme", (String) themeSelector.getSelectedItem());
                frame.dispose();
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(themeSelector);
        frame.setVisible(true);
    }

    /**
     * 
     * Method that chooses the theme to be applied to the application.
     * 
     * 
     * @return theme The theme to be applied.
     */
    private static String chooseTheme() {
        return userPrefs.get("theme", "default");
    }

    /**
     * 
     * Method that sets the frame.
     * 
     * 
     * @param frame
     */
    public static void setFrame(JFrame frame) {
        Themes.frame = frame;
    }

    /**
     * 
     * Method that applies the theme to the application.
     * 
     * 
     * @param theme The theme to be applied.
     */
    public static void applyTheme(String theme) {
        try {
            switch (theme) {
                case "nimbus":
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                case "metal":
                    MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    break;
                case "motif":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    break;
                case "windows":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                case "gtk":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    break;
                case "gtk+":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    break;
                case "macos":
                    UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
                    break;
                case "system":
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                case "dark":
                    MetalLookAndFeel.setCurrentTheme(new DarkTheme());
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    break;
                default:
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            }
        } catch (Exception e) {
            System.err.println("Error setting theme " + theme + ": " + e.getMessage());
        }

        String username = Andie.getUser();

        if (username != null) {

            DatabaseManager dbManager = new DatabaseManager();

            SetThemeQuery setTheme = new SetThemeQuery(dbManager, loadingWindow, username, theme);
            setTheme.execute();

        }

    }
}
