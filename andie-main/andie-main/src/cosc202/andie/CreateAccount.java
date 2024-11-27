package cosc202.andie;

import javax.swing.*;

import cosc202.andie.db.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * <p>
 * Create account during sign up
 * </p>
 * 
 * <p>
 * The user can create an account with a username and password
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC
 * BY-NC-SA4.0</a>
 * </p>
 * 
 * @version 1.0
 * @see DatabaseConnection
 * @see Andie
 * @see Login
 * 
 * @author Josh Lawson
 */

/**
 * <p>
 * Create account during sign up
 * </p>
 * 
 * <p>
 * The user can create an account with a username and password
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC
 * BY-NC-SA4.0</a>
 * </p>
 * 
 * @version 1.0
 *          {@link db.DatabaseConnection}
 * @see Andie
 * @see Login
 * 
 * @author Josh Lawson
 */

public class CreateAccount {
    JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    protected static ResourceBundle bundle;

    private LoadingWindow loadingWindow;

    public CreateAccount() {
        initialize();
        LoadingWindow loadingWindow = new LoadingWindow();
        this.loadingWindow = loadingWindow;
    }

    private void initialize() {

        frame = new JFrame("Create Account");
        frame.setSize(350, 200);

        frame.setBounds(110, 100, 350, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usernameField = new JTextField();
        usernameField.setBounds(120, 28, 130, 26);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 66, 130, 26);
        frame.getContentPane().add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(50, 33, 71, 16);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(50, 71, 71, 16);
        frame.getContentPane().add(lblPassword);

        JLabel errorMessage = new JLabel("Username already taken. Please try again.");
        errorMessage.setBounds(45, 165, 300, 26);
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        frame.getContentPane().add(errorMessage);

        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorMessage.setVisible(false);
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                DatabaseManager dbManager = new DatabaseManager();
                UsernameExistsQuery usernameExists = new UsernameExistsQuery(dbManager, username);
                Boolean usernameExistsResult = usernameExists.check();

                // Check if the username already exists
                if (usernameExistsResult) {
                    // Notify the user that the username is taken
                    System.out.println("Username already taken.");
                    errorMessage.setVisible(true);
                } else {
                    frame.dispose();
                    loadingWindow.start();
                    AddUserQuery addUser = new AddUserQuery(dbManager, loadingWindow, username, password);
                    // Attempt to add the new user
                    addUser.execute();
                }
            }
        });

        btnCreateAccount.setBounds(110, 104, 150, 35);
        frame.getContentPane().add(btnCreateAccount);
    }

    /**
     * <p>
     * Sets the resource bundle for the class.
     * </p>
     * 
     * @param language The language to set the resource bundle to.
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
