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
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

  /** A list of actions for the File menu. */
  protected ArrayList<Action> actions;

  /**
   * <p>
   * Create a set of File menu actions.
   * </p>
   *
   */
  public FileActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new FileOpenAction(
        Andie.bundle.getString("open_title"),
        null,
        Andie.bundle.getString("open_desc"),
        Integer.valueOf(KeyEvent.VK_O)
      )
    );
    actions.add(
      new FileSaveAction(
        Andie.bundle.getString("save_title"),
        null,
        Andie.bundle.getString("save_desc"),
        Integer.valueOf(KeyEvent.VK_S)
      )
    );
    actions.add(
      new FileSaveAsAction(
        Andie.bundle.getString("save_as_title"),
        null,
        Andie.bundle.getString("save_as_desc"),
        Integer.valueOf(KeyEvent.VK_A)
      )
    );
    actions.add(
      new FileExportAction(
        Andie.bundle.getString("export_title"),
        null,
        Andie.bundle.getString("export_desc"),
        Integer.valueOf(KeyEvent.VK_E)
      )
    );
    actions.add(
      new FileExitAction(
        Andie.bundle.getString("exit_title"),
        null,
        Andie.bundle.getString("exit_desc"),
        Integer.valueOf(KeyEvent.VK_X)
      )
    );
  }

  /**
   * <p>
   * Get the open action.
   * </p>
   *
   * @return
   */
  public Action getOpenAction() {
    return actions.get(0);
  }

  /**
   * <p>
   * Get the save action.
   * </p>
   *
   * @return
   */
  public Action getSaveAction() {
    return actions.get(1);
  }

  /**
   * <p>
   * Create a menu contianing the list of File actions.
   * </p>
   *
   * @return The File menu UI element.
   */
  public JMenu createMenu() {
    JMenu fileMenu = new JMenu(Andie.bundle.getString("file_title"));
    for (Action action : actions) {
      fileMenu.add(new JMenuItem(action));
    }

    return fileMenu;
  }

  /**
   * <p>
   * Action to open an image from file.
   * </p>
   *
   * @see EditableImage#open(String)
   */
  public class FileOpenAction extends ImageAction {

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
    FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     *
     * checking that the file that is opened is a png or a jpg, if not
     * it will throw an exception and tell the user that they are trying to open the
     * wrong file type
     *
     *
     * @param fileExtension
     * @throws Exception
     */

    public void validateFileType(String fileExtension) throws Exception {
      if (
        !fileExtension.equalsIgnoreCase("png") &&
        !fileExtension.equalsIgnoreCase("jpg")
      ) {
        throw new Exception(Andie.bundle.getString("incorrect_file_error"));
      }
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
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String imageFilepath = fileChooser
            .getSelectedFile()
            .getCanonicalPath();
          String fileExtension = imageFilepath.substring(
            imageFilepath.lastIndexOf(".") + 1
          );
          validateFileType(fileExtension);
          target.getImage().open(imageFilepath);
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(
            target,
            ex.getMessage(),
            Andie.bundle.getString("error_title"),
            JOptionPane.ERROR_MESSAGE
          );
        }
      }

      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * <p>
   * Action to save an image to its current file location.
   * </p>
   *
   * @see EditableImage#save()
   */
  public class FileSaveAction extends ImageAction {

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
    FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)
        );
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
        try {
          target.getImage().save();
        } catch (Exception ex) {
          System.exit(1);
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

  /**
   * <p>
   * Action to save an image to a new file location.
   * </p>
   *
   * @see EditableImage#saveAs(String)
   */
  public class FileSaveAsAction extends ImageAction {

    /**
     * <p>
     * Create a new file-save-as action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileSaveAsAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the file-save-as action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FileSaveAsAction is triggered.
     * It prompts the user to select a file and saves the image to it.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(target);

        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            String imageFilepath = fileChooser
              .getSelectedFile()
              .getCanonicalPath();
            target.getImage().saveAs(imageFilepath);
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

  /**
   * Action to export an image to a new file location with a different format.
   * If no file format is specified by the user, the image is saved as a PNG file
   * by default.
   * If the user specifies another extension in the file name like .JPG or .BMP,
   * the image is saved in that format.
   */

  public class FileExportAction extends ImageAction {

    /**
     * Create a new file-export action.
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileExportAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * Callback for when the file-export action is triggered.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      try {
        exportImage();
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(
          null,
          Andie.bundle.getString("export_error"),
          Andie.bundle.getString("error_title"),
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }

  private void exportImage() throws Exception {
    if (
      ImageAction.getTarget() == null ||
      !ImageAction.getTarget().getImage().hasImage()
    ) {
      try {
        Andie.createErrorMessage(Andie.bundle.getString("no_file_error"));
      } catch (Exception ex) {
        ex.printStackTrace();
        System.exit(1);
      }
    } else {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Export Image");

      // Change filter to "All Files"
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "All Files",
        "jpg",
        "jpeg",
        "png",
        "bmp"
      );
      fileChooser.setFileFilter(filter);

      int result = fileChooser.showSaveDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String exportFilename = file.getCanonicalPath();

        // Get the user-specified extension or default to "png"
        String extension = "png";
        if (exportFilename.contains(".")) {
          extension =
            exportFilename.substring(exportFilename.lastIndexOf('.') + 1);
        } else {
          exportFilename += ".png";
        }

        ImageAction.getTarget().getImage().export(exportFilename, extension);
      }
    }
  }

  /**
   * <p>
   * Action to quit the ANDIE application.
   * </p>
   */
  public class FileExitAction extends AbstractAction {

    /**
     * <p>
     * Create a new file-exit action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK)
        );
      putValue(SHORT_DESCRIPTION, desc);
      putValue(MNEMONIC_KEY, mnemonic);
    }

    /**
     * <p>
     * Callback for when the file-exit action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FileExitAction is triggered.
     * It quits the program.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
}
