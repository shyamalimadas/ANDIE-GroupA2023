package cosc202.andie;

import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 *
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Lucy Hadden
 * @version 1.0
 */
public class MacroActions {

  /** A list of actions for the File menu. */
  protected ArrayList<Action> actions;

  /**
   * <p>
   * Create a set of File menu actions.
   * </p>
   *
   */
  public MacroActions() {
    actions = new ArrayList<Action>();
    actions.add(
        new StartRecording(
            Andie.bundle.getString("start_recording_title"),
            null,
            Andie.bundle.getString("start_recording_desc"),
            Integer.valueOf(KeyEvent.VK_Q)));
    actions.add(
        new MacroSaveAction(
            Andie.bundle.getString("stop_recording_title"),
            null,
            Andie.bundle.getString("stop_recording_desc"),
            Integer.valueOf(KeyEvent.VK_Q)));
    actions.add(
        new MacroOpenAction(
            Andie.bundle.getString("replay_title"),
            null,
            Andie.bundle.getString("replay_desc"),
            Integer.valueOf(KeyEvent.VK_J)));
  }

  /**
   * <p>
   * Get the open action.
   * </p>
   *
   * @return
   */
  public Action getStartAction() {
    return actions.get(0);
  }

  /**
   * <p>
   * Get the save action.
   * </p>
   *
   * @return
   */
  public Action getStopAction() {
    return actions.get(1);
  }

  /**
   * <p>
   * Get the save action.
   * </p>
   *
   * @return
   */
  public Action getReplayAction() {
    return actions.get(2);
  }

  /**
   * <p>
   * Create a menu contianing the list of File actions.
   * </p>
   *
   * @return The File menu UI element.
   */
  public JMenu createMenu() {
    JMenu macroMenu = new JMenu(Andie.bundle.getString("macros_title"));

    for (Action action : actions) {
      macroMenu.add(new JMenuItem(action));
    }

    return macroMenu;
  }

  /**
   * <p>
   * Action to open an image from file.
   * </p>
   *
   * @see EditableImage#OpenMacro(String)
   */
  public class MacroOpenAction extends ImageAction {

    /**
     * <p>
     * Create a new file-open action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MacroOpenAction(
        String name,
        ImageIcon icon,
        String desc,
        Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * <p>
     * Callback for when the file-open action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FileOpenAction is triggered.
     * It prompts the user to select a file and opens it as an image.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(target);

        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            String imageFilepath = fileChooser
                .getSelectedFile()
                .getCanonicalPath();
            target.getImage().loadMacro(imageFilepath);
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                target,
                ex.getMessage(),
                Andie.bundle.getString("error_title"),
                JOptionPane.ERROR_MESSAGE);
          }
        }
        target.repaint();
        target.getParent().revalidate();
      } else {
        try {
          Andie.createErrorMessage(Andie.bundle.getString("no_file_error"));
        } catch (Exception ex) {
          ex.printStackTrace();
          System.exit(1);
        }
      }
    }
  }

  /**
   * <p>
   * Action to open an image from file.
   * </p>
   *
   * {@link EditableImage#OpenMacro(String)}
   */
  public class StartRecording extends ImageAction {

    /**
     * <p>
     * Create a new file-open action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    StartRecording(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * <p>
     * Callback for when the file-open action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FileOpenAction is triggered.
     * It prompts the user to select a file and opens it as an image.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        EditableImage.recordedActions.clear();
        EditableImage.isRecording = true;
      } else {
        try {
          Andie.createErrorMessage(Andie.bundle.getString("no_file_error"));
        } catch (Exception ex) {
          ex.printStackTrace();
          System.exit(1);
        }
      }
    }
  }

  /**
   * <p>
   * Action to save an image to its current file location.
   * </p>
   *
   * {@link EditableImage#saveMacro() }
   */
  public class MacroSaveAction extends ImageAction {

    /**
     * <p>
     * Create a new file-save action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MacroSaveAction(
        String name,
        ImageIcon icon,
        String desc,
        Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * <p>
     * Callback for when the file-save action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FileSaveAction is triggered.
     * It saves the image to its original filepath.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        EditableImage.isRecording = false;
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(target);

        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            String imageFilepath = fileChooser
                .getSelectedFile()
                .getCanonicalPath();
            target.getImage().saveMacro(imageFilepath);
          } catch (Exception ex) {
            System.exit(1);
          }
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
  }
}
