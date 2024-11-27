package cosc202.andie;

import java.awt.event.*;
import java.util.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * lucy changes test
 * </p>
 *
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
public class ColourActions {

  /** A list of actions for the Colour menu. */
  protected ArrayList<Action> actions;

  /**
   * <p>
   * Create a set of Colour menu actions.
   * creates the shortcut of 'b' when the user wishes to access the brightness
   * filter
   * </p>
   */
  public ColourActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new ConvertToGreyAction(
        Andie.bundle.getString("greyscale_title"),
        null,
        Andie.bundle.getString("greyscale_desc"),
        Integer.valueOf(KeyEvent.VK_G)
      )
    );
    actions.add(
      new BrightnessAdjustmentAction(
        Andie.bundle.getString("brightness_title"),
        null,
        Andie.bundle.getString("brightness_desc"),
        Integer.valueOf(KeyEvent.VK_COMMA)
      )
    );
    actions.add(
      new ContrastAdjustmentAction(
        Andie.bundle.getString("contrast_title"),
        null,
        Andie.bundle.getString("contrast_desc"),
        Integer.valueOf(KeyEvent.VK_K)
      )
    );
  }

  /**
   * <p>
   * Create a menu contianing the list of Colour actions.
   * </p>
   *
   * @return The colour menu UI element.
   */
  public JMenu createMenu() {
    JMenu fileMenu = new JMenu(Andie.bundle.getString("colour_title"));
    for (Action action : actions) {
      fileMenu.add(new JMenuItem(action));
    }

    return fileMenu;
  }

  /**
   * <p>
   * Action to convert an image to greyscale.
   * </p>
   *
   * @see ConvertToGrey
   */
  public class ConvertToGreyAction extends ImageAction {

    /**
     * <p>
     * Create a new convert-to-grey action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ConvertToGreyAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the convert-to-grey action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ConvertToGreyAction is triggered.been
     * It changes the image to greyscale.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new ConvertToGrey());
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
   * Action that adjusts the brightness of a given image
   * </p>
   *
   * @see BrightnessFilter
   */
  public class BrightnessAdjustmentAction extends ImageAction {

    /**
     * <p>
     * Create a new brightness-adjustment filter instance
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    BrightnessAdjustmentAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_DOWN_MASK)
        );
    }

    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        // Pop-up dialog box to ask for the specified brightness value.
        JSlider brightnessSlider = new JSlider(
          JSlider.HORIZONTAL,
          -100,
          200,
          0
        );
        brightnessSlider.setMajorTickSpacing(50);
        brightnessSlider.setMinorTickSpacing(10);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);

        int option = JOptionPane.showOptionDialog(
          null,
          brightnessSlider,
          Andie.bundle.getString("brightness_menu_title"),
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          null,
          null
        );

        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {
          return;
        } else if (option == JOptionPane.OK_OPTION) {
          int brightness = brightnessSlider.getValue();

          // Create and apply the filter
          target.getImage().apply(new BrightnessFilter(brightness));
          target.repaint();
          target.getParent().revalidate();
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
   * Action that adjusts the contrast of a given image
   * </p>
   *
   * @see ContrastFilter
   */
  public class ContrastAdjustmentAction extends ImageAction {

    /**
     * <p>
     * Create a new contrast-adjustment filter instance
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ContrastAdjustmentAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK)
        );
    }

    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        // Pop-up dialog box to ask for the specified contrast value.
        JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, 90, 200, 90);
        contrastSlider.setMajorTickSpacing(50);
        contrastSlider.setMinorTickSpacing(10);
        contrastSlider.setPaintTicks(true);
        contrastSlider.setPaintLabels(true);

        int option = JOptionPane.showOptionDialog(
          null,
          contrastSlider,
          Andie.bundle.getString("contrast_menu_title"),
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          null,
          null
        );

        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {
          return;
        } else if (option == JOptionPane.OK_OPTION) {
          int contrast = contrastSlider.getValue();

          // Create and apply the filter
          target.getImage().apply(new ContrastFilter(contrast));
          target.repaint();
          target.getParent().revalidate();
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
