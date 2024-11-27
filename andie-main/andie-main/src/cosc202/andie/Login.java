package cosc202.andie;

import javax.swing.*;

import cosc202.andie.db.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * 
 * <p>
 * This class is used to create a login window.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class Login {
    JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    protected static ResourceBundle bundle;

    private LoadingWindow loadingWindow;

    /**
     * <p>
     * Create a Login object.
     * </p>
     */
    public Login() {
        initialize();
        LoadingWindow loadingWindow = new LoadingWindow();
        this.loadingWindow = loadingWindow;
    }

    /**
     * <p>
     * Start the Login window.
     * </p>
     */
    private void initialize() {
        frame = new JFrame("Login");
        frame.setBounds(100, 100, 500, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel message = new JLabel("Please enter your username and password or create an account");
        message.setBounds(50, 20, 400, 26);
        frame.getContentPane().add(message);

        JLabel errorMessage = new JLabel("Invalid username or password. Please try again or create an account.");
        errorMessage.setBounds(33, 220, 440, 36);
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        frame.getContentPane().add(errorMessage);

        usernameField = new JTextField();
        usernameField.setBounds(120, 78, 130, 26);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 116, 130, 26);
        frame.getContentPane().add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(50, 83, 100, 16);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(50, 121, 100, 16);
        frame.getContentPane().add(lblPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorMessage.setVisible(false);
                String username = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                DatabaseManager dbManager = new DatabaseManager();
                UsernameExistsQuery usernameExistsQuery = new UsernameExistsQuery(dbManager, username);
                Boolean usernameExists = usernameExistsQuery.check();

                PasswordQuery passwordQuery = new PasswordQuery(dbManager, username);
                String userPassword = passwordQuery.getPassword();

                // Check the user's credentials
                if (usernameExists && userPassword.equals(enteredPassword)) {
                    // Successful login
                    System.out.println("Login successful!");
                    Andie.setUser(username);

                    frame.dispose();
                    loadingWindow.start();
                    try {

                        GetThemeQuery getThemeQuery = new GetThemeQuery(dbManager, loadingWindow, username);
                        getThemeQuery.execute();

                        //GetLanguageQuery getLanguageQuery = new GetLanguageQuery(dbManager, loadingWindow, username);
                        //getLanguageQuery.execute();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    // Failed login
                    System.out.println("Invalid username or password. Please try again or create an account.");
                    errorMessage.setVisible(true);
                }
            }
        });
        btnLogin.setBounds(110, 160, 150, 35);
        frame.getContentPane().add(btnLogin);

        JButton skip = new JButton("Skip");
        skip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    Andie.createLanguageGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        skip.setBounds(300, 160, 150, 35);
        frame.getContentPane().add(skip);

        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                CreateAccount createAccountWindow = new CreateAccount();
                createAccountWindow.frame.setVisible(true);
            }
        });
        btnCreateAccount.setBounds(300, 77, 150, 35);
        frame.getContentPane().add(btnCreateAccount);
    }

    /**
     * <p>
     * Sets the resource bundle to the specified language.
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
