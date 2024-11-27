package cosc202.andie;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Preferences menu.
 * </p>
 *
 * <p>
 * The Preference menu contains actions that affect some features of the
 * application for each user
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Josh Lawson
 *
 * @version 1.0
 */
public class PreferencesActions {

  protected ArrayList<Action> actions;
  protected LoadingWindow loadingWindow;

  /**
   * <p>
   * Construct a PreferencesActions object.
   * </p>
   */
  public PreferencesActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new ThemeAction(
        Andie.bundle.getString("themes_title"),
        null,
        Andie.bundle.getString("rotate_left_desc"),
        Integer.valueOf(KeyEvent.VK_T)
      )
    );
    actions.add(
      new LanguageAction(
        Andie.bundle.getString("languages_title"),
        null,
        Andie.bundle.getString("rotate_right_desc"),
        Integer.valueOf(KeyEvent.VK_PERIOD)
      )
    );
  }

  /**
   * <p>
   * Create a menu of actions.
   * </p>
   *
   * @return The menu.
   */
  public JMenu createMenu() {
    //this.loadingWindow = loadingWindow;
    JMenu transformMenu = new JMenu(
      Andie.bundle.getString("preferences_title")
    );
    for (Action action : actions) {
      transformMenu.add(new JMenuItem(action));
    }
    return transformMenu;
  }

  /**
   * <p>
   * Action to apply a theme to the application.
   * </p>
   *
   */
  public class ThemeAction extends OtherAction {

    /**
     * <p>
     * Apply a theme to the application.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ThemeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the rotate left action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ThemeAction is triggered.
     * It applies a theme to the application.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      try {
        Themes.showThemeSelector(newTheme -> {
          Themes.applyTheme(newTheme);
          SwingUtilities.updateComponentTreeUI(Andie.frame);
          Andie.frame.pack();
        });
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * <p>
   * Action to apply a language to the application.
   * </p>
   *
   */
  public class LanguageAction extends OtherAction {

    /**
     * <p>
     * Apply a language to the application.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    LanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the rotate left action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the LanguageAction is triggered.
     * It applies a language to the application.
     *
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      try {
        Andie.createLanguageGUI();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
