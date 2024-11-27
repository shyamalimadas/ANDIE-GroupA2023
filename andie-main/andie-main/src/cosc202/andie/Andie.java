package cosc202.andie;

import cosc202.andie.db.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.imageio.*;
import javax.naming.ldap.ManageReferralControl;
import javax.swing.*;
import javax.swing.JComboBox;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 *
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    // The ResourceBundle for the current locale
    public static ResourceBundle bundle;

    public static JFrame frame;
    protected static JToolBar toolbar;
    protected static ImagePanel imagePanel;
    protected static JScrollPane scrollPane;

    private static String user;
    private static String theme;

    protected static LoadingWindow loadingWindow;

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     *
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     *
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see TransformActions
     *
     * @throws Exception if something goes wrong.
     */
    public static void createAndShowGUI() throws Exception {

        Themes.applyTheme(theme);


        // Set up the main GUI frame
        frame = new JFrame("ANDIE");
        // Show tutorial overlay
        showTutorialOverlay();
        
        if (user != null) {
            System.out.println("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n\nTheme is " + theme + "\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n");
        }
        
        // The main content area is an ImagePanel
        imagePanel = new ImagePanel();

        // icon.png updated to be our specified group 'A' icon
        Image image = ImageIO.read(
                Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageAction.setTarget(imagePanel);
        scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();
        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        MacroActions macroAtions = new MacroActions();
        menuBar.add(macroAtions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // View actions control how the image is transformed, but do not alter its
        // actual
        // content
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        JButton stickerButton = new JButton(
                Andie.bundle.getString("stickers_title"),
                null);
        stickerButton.setContentAreaFilled(false);
        stickerButton.setOpaque(false);
        stickerButton.setBorderPainted(false);
        stickerButton.setFocusPainted(false);

        stickerButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (ImagePanel.image.hasImage()) {
                            if (!Stickers.stickerMode) {
                                Andie.imagePanel.setSelection(null);
                                Andie.imagePanel.repaint();

                                new Stickers();
                            }
                        } else {
                            try {
                                Andie.createErrorMessage(Andie.bundle.getString("no_file_error"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }
                        }
                    }
                });
        menuBar.add(stickerButton);

        PreferencesActions preferencesAction = new PreferencesActions();
        menuBar.add(preferencesAction.createMenu());

        HelpMenuAction helpMenu = new HelpMenuAction();
        menuBar.add(helpMenu.createMenu());

        toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.PAGE_START);

        frame.setJMenuBar(menuBar);

        frame.pack();

        // positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        SwingUtilities.updateComponentTreeUI(frame);
        frame.revalidate();
        frame.repaint();


    }

    /**
     * Shows the tutorial overlay pop-up.
     */
    private static void showTutorialOverlay() {
        JWindow overlayWindow = new JWindow(frame);
        overlayWindow.setLayout(new BorderLayout());

        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBackground(new Color(255, 255, 255, 200));
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel overlayLabel = new JLabel(Andie.bundle.getString("andie_tutorial_title"));
        overlayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        overlayLabel.setFont(new Font("", Font.BOLD, 16));

        JTextArea tutorialTextArea = new JTextArea();
        tutorialTextArea.setEditable(false);
        tutorialTextArea.setLineWrap(true);
        tutorialTextArea.setWrapStyleWord(true);
        tutorialTextArea.setFont(new Font("", Font.PLAIN, 14));
        tutorialTextArea.setText(Andie.bundle.getString("andie_tutorial_desc"));

        JScrollPane scrollPane = new JScrollPane(tutorialTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JButton closeButton = new JButton(Andie.bundle.getString("andie_tutorial_close"));
        closeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        overlayWindow.dispose();
                    }
                });

        overlayPanel.add(overlayLabel, BorderLayout.NORTH);
        overlayPanel.add(scrollPane, BorderLayout.CENTER);
        overlayPanel.add(closeButton, BorderLayout.SOUTH);

        overlayWindow.getContentPane().add(overlayPanel);
        overlayWindow.pack();

        overlayWindow.setVisible(true);

        // positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - overlayWindow.getSize().width) / 2;
        int y = (screenSize.height - overlayWindow.getSize().height) / 2;
        overlayWindow.setLocation(x, y);

    }

    /**
     * <p>
     * Disposes the main GUI frame.
     * </p>
     */
    public static void disposeFrame() {
        if (frame != null) {
            frame.dispose();
        }
    }

    /**
     * <p>
     * Launches the language selection GUI for the ANDIE program.
     * </p>
     *
     * <p>
     * This method sets up an interface consisting of languages which the user
     * can select to change all the program functions to the selected language.
     * </p>
     *
     * @author Ivan Chevtchenko
     * @throws Exception if something goes wrong.
     */

    public static void createLanguageGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");
        Image image = ImageIO.read(
                Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setSize(250, 400);

        // positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 28, 30));

        JComboBox<String> languageList = new JComboBox<String>();
        languageList.addItem("English (NZ)");
        languageList.addItem("Te Reo Māori");
        languageList.addItem("Русский");
        languageList.addItem("Español");
        languageList.addItem("Deutsch");
        languageList.addItem("Français");
        languageList.addItem("Japanese");
        languageList.setSelectedIndex(-1);
        languageList.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        @SuppressWarnings("unchecked")
                        JComboBox<String> combo = (JComboBox<String>) event.getSource();
                        String selectedLanguage = (String) combo.getSelectedItem();

                        if (selectedLanguage.equals("English (NZ)")) {
                            Locale locale = new Locale("en", "NZ");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Te Reo Māori")) {
                            Locale locale = new Locale("mi", "NZ");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Русский")) {
                            Locale locale = new Locale("ru", "RU");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Español")) {
                            Locale locale = new Locale("es", "ES");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Deutsch")) {
                            Locale locale = new Locale("de", "DE");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Français")) {
                            Locale locale = new Locale("fr", "FR");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (selectedLanguage.equals("Japanese")) {
                            Locale locale = new Locale("ja", "JP");
                            bundle = ResourceBundle.getBundle("MessageBundle", locale);
                        }
                        if (user != null) {
                            try {
                                loadingWindow = new LoadingWindow();
                                loadingWindow.start();
                                if (frame != null) {
                                    disposeFrame();
                                }
                                frame.setVisible(false);
                                frame.dispose();

                                DatabaseManager dbManager = new DatabaseManager();
                                SetLanguageQuery setLanguageQuery = new SetLanguageQuery(
                                        dbManager,
                                        loadingWindow,
                                        user,
                                        selectedLanguage);
                                setLanguageQuery.execute();

                                createAndShowGUI();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }
                        } else {
                            try {
                                if (frame != null) {
                                    disposeFrame();
                                }
                                frame.setVisible(false);
                                frame.dispose();

                                createAndShowGUI();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }
                        }
                    }
                });

        BufferedImage bufferedImage = ImageIO.read(
                new File("src/cosc202/andie/resources/icons/language.png"));
        Image languageImage = bufferedImage.getScaledInstance(
                45,
                45,
                Image.SCALE_DEFAULT);

        JLabel languageImageLabel = new JLabel(new ImageIcon(languageImage));

        JLabel languageLabel = new JLabel("Select Language");
        languageLabel.setFont(new Font("", Font.BOLD, 15));
        languageList.setFont(new Font("", Font.BOLD, 14));

        // panel.setBackground(new Color(237, 237, 255));
        panel.add(languageImageLabel);
        panel.add(languageLabel);
        panel.add(languageList);

        frame.add(panel);
        frame.setVisible(true);

    }

    /**
     * <p>
     * Creates an error popup with a certain message.
     * </p>
     *
     * <p>
     * Creates a popup when the method is run with a certain String as input.
     * </p>
     *
     * @param error error message
     */

    public static void createErrorMessage(String error) {
        JOptionPane.showMessageDialog(
                frame,
                error,
                bundle.getString("error_title"),
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * <p>
     * Subclass that extends JButton to
     * create a button with an icon to be added to the tool bar
     * </p>
     *
     * <p>
     * Purpose of this class it to set the size of the icon,
     * so that all icons in the tool bar have a consistent size
     * </p>
     *
     */
    public static class IconButton extends JButton {

        // Default icon width
        private int iconWidth = 24;
        // Default icon height
        private int iconHeight = 24;

        /**
         * <p>
         * Constructor for IconButton
         * </p>
         *
         * @param action: Action to be performed when button is clicked
         * @param icon:   ImageIcon to be displayed on button
         */
        public IconButton(Action action, ImageIcon icon) {
            super(action);
            setIcon(resizeIcon(icon, iconWidth, iconHeight));
            setText("");
        }

        /**
         * <p>
         * Resizes an ImageIcon to a specified width and height
         * </p>
         *
         * @param icon:   ImageIcon to be resized
         * @param width:  Width of resized icon
         * @param height: Height of resized icon
         *
         * @return: Resized ImageIcon
         */
        private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        }
    }

    /**
     * <p>
     * Creates the toolbar for the image editor
     * </p>
     *
     * returning the JToolBar containing all the buttons for the toolbar
     */
    protected static JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar("");
        toolbar.setFloatable(false);

        FileActions fileActions = new FileActions();
        EditActions editActions = new EditActions();
        MacroActions macroActions = new MacroActions();
        ViewActions viewActions = new ViewActions();

        ImageIcon openIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/open.png"));
        JButton openImageButton = new IconButton(
                fileActions.getOpenAction(),
                openIcon);
        openImageButton.setToolTipText(Andie.bundle.getString("open_desc"));

        ImageIcon saveIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/save.png"));
        JButton saveImageButton = new IconButton(
                fileActions.getSaveAction(),
                saveIcon);
        saveImageButton.setToolTipText(Andie.bundle.getString("save_desc"));

        ImageIcon undoIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/undo.png"));
        JButton undoButton = new IconButton(editActions.getUndo(), undoIcon);
        undoButton.setToolTipText(Andie.bundle.getString("undo_desc"));

        ImageIcon redoIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/redo.png"));
        JButton redoButton = new IconButton(editActions.getRedo(), redoIcon);
        redoButton.setToolTipText(Andie.bundle.getString("redo_desc"));

        ImageIcon zoomInIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/zoom_in.png"));
        JButton zoomInButton = new IconButton(viewActions.getZoomIn(), zoomInIcon);
        zoomInButton.setToolTipText(Andie.bundle.getString("zoom_in_desc"));

        ImageIcon zoomOutIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/zoom_out.png"));
        JButton zoomOutButton = new IconButton(
                viewActions.getZoomOut(),
                zoomOutIcon);
        zoomOutButton.setToolTipText(Andie.bundle.getString("zoom_out_desc"));

        ImageIcon zoomFullIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/zoom_full.png"));
        JButton zoomFullButton = new IconButton(
                viewActions.getZoomFull(),
                zoomFullIcon);
        zoomFullButton.setToolTipText(Andie.bundle.getString("zoom_full_desc"));

        ImageIcon playIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/play.png"));
        JButton playIconButton = new IconButton(
                macroActions.getStartAction(),
                playIcon);
        playIconButton.setToolTipText(Andie.bundle.getString("start_recording_title"));

        ImageIcon stopIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/stop.png"));
        JButton stopIconButton = new IconButton(
                macroActions.getStopAction(),
                stopIcon);
        stopIconButton.setToolTipText(Andie.bundle.getString("stop_recording_title"));

        ImageIcon replayIcon = new ImageIcon(
                Andie.class.getResource("resources/icons/replay.png"));
        JButton replayIconButton = new IconButton(
                macroActions.getReplayAction(),
                replayIcon);
        replayIconButton.setToolTipText(Andie.bundle.getString("replay_title"));

        toolbar.add(openImageButton);
        toolbar.add(saveImageButton);
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.add(zoomInButton);
        toolbar.add(zoomOutButton);
        toolbar.add(zoomFullButton);
        toolbar.add(playIconButton);
        toolbar.add(stopIconButton);
        toolbar.add(replayIconButton);

        return toolbar;
    }

    /**
     * <p>
     * Method that gets the user's username
     * </p>
     *
     * @return user's username
     */
    public static String getUser() {
        return user;
    }

    /**
     * <p>
     * Method that sets the user's username
     * </p>
     *
     * @param user: user's username
     */
    public static void setUser(String user) {
        Andie.user = user;
    }

    /**
     * <p>
     * Method that sets the theme of the GUI
     * </p>
     * 
     * @param theme
     */
    public static void setTheme(String theme) {
        Andie.theme = theme;
    }

    /**
     * <p>
     * Method that gets the theme of the GUI
     * </p>
     * 
     * @return theme
     */
    public static String getTheme() {
        return theme;
    }

    /**
     * <p>
     * Method that sets the ResourceBundle
     * </p>
     *
     * @param ResourceBundle
     */
    public static void setResourceBundle(ResourceBundle bundle) {
        Andie.bundle = bundle;
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     *
     * <p>
     * Creates and launches the language GUI in a separate thread.
     * As a result, this is essentially a wrapper around
     * {@code createLanguageGUI()}.
     * </p>
     *
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createLanguageGUI()
     */
    public static void main(String[] args) throws Exception {
        Login window = new Login();
        window.frame.setVisible(true);
        // javax.swing.SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // try {
        // createLanguageGUI();
        // } catch (Exception ex) {
        // System.out.println(ex.getMessage());
        // ex.printStackTrace();
        // System.exit(1);
        // }
        // }
        // });

    }
}
