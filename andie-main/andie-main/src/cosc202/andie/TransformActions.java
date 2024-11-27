package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * <p>
 * Actions provided by the Transform menu.
 * </p>
 *
 * <p>
 * The Transform menu contains actions that affect how the image is transformed
 * in the application.
 * These actions do not affect the contents of the image itself, just the way it
 * is displayed.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @version 1.1
 */
public class TransformActions {

  /**
   * A list of actions for the Transform menu.
   */
  protected ArrayList<Action> actions;

  /**
   * <p>
   * Create a set of Transform menu actions.
   * </p>
   */

  public TransformActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new RotateLeftAction(
        Andie.bundle.getString("rotate_left_title"),
        null,
        Andie.bundle.getString("rotate_left_desc"),
        Integer.valueOf(KeyEvent.VK_CLOSE_BRACKET)
      )
    );
    actions.add(
      new RotateRightAction(
        Andie.bundle.getString("rotate_right_title"),
        null,
        Andie.bundle.getString("rotate_right_desc"),
        Integer.valueOf(KeyEvent.VK_OPEN_BRACKET)
      )
    );
    actions.add(
      new Rotate180Action(
        Andie.bundle.getString("rotate_180_title"),
        null,
        Andie.bundle.getString("rotate_180_desc"),
        Integer.valueOf(KeyEvent.VK_F)
      )
    );
    actions.add(
      new FlipHorizontalAction(
        Andie.bundle.getString("flip_horizontal_title"),
        null,
        Andie.bundle.getString("flip_horizontal_desc"),
        Integer.valueOf(KeyEvent.VK_H)
      )
    );
    actions.add(
      new FlipVerticalAction(
        Andie.bundle.getString("flip_vertical_title"),
        null,
        Andie.bundle.getString("flip_vertical_desc"),
        Integer.valueOf(KeyEvent.VK_V)
      )
    );
    actions.add(
      new ResizeAction(
        Andie.bundle.getString("resize_title"),
        null,
        Andie.bundle.getString("resize_desc"),
        Integer.valueOf(KeyEvent.VK_I)
      )
    );
  }

  /**
   * <p>
   * Create a menu containing the list of Transform actions.
   * </p>
   *
   * @return The transform menu UI element.
   */
  public JMenu createMenu() {
    JMenu transformMenu = new JMenu(Andie.bundle.getString("transform_title"));
    for (Action action : actions) {
      transformMenu.add(new JMenuItem(action));
    }

    return transformMenu;
  }

  /**
   * <p>
   * Action to rotation a buffeered image by 90 degrees left.
   * </p>
   *
   * see rotate()
   */
  public class RotateLeftAction extends ImageAction {

    /**
     * <p>
     * Rotates the image 90 degrees left.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    RotateLeftAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(
            KeyEvent.VK_CLOSE_BRACKET,
            InputEvent.CTRL_DOWN_MASK
          )
        );
    }

    /**
     * <p>
     * Callback for when the rotate left action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the RotateLeftAction is triggered.
     * It rotates the image 90 degrees left.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new Rotate(270));
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
   * Action to rotation a buffered image by 90 degrees right.
   * </p>
   *
   * see rotate()
   */
  public class RotateRightAction extends ImageAction {

    /**
     * <p>
     * Rotates the image 90 degrees right.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    RotateRightAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(
            KeyEvent.VK_OPEN_BRACKET,
            InputEvent.CTRL_DOWN_MASK
          )
        );
    }

    /**
     * <p>
     * Callback for when the rotate right action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the RotateRightAction is triggered.
     * It rotates the image 90 degrees right.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new Rotate(90));
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
   * Action to rotation a buffered image by 180 degrees.
   * </p>
   *
   * see rotate()
   */
  public class Rotate180Action extends ImageAction {

    /**
     * <p>
     * Rotates the image 180 degrees.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    Rotate180Action(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the rotate 180 action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the Rotate180Action is triggered.
     * It rotates the image 180 degrees.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new Rotate(180));
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
   * Action to flip horizontal a buffered image
   * </p>
   *
   * see fliphorizontal()
   */
  public class FlipHorizontalAction extends ImageAction {

    /**
     * <p>
     * Create a new flip horizontal action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FlipHorizontalAction(
      String name,
      ImageIcon icon,
      String desc,
      int mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the flip horizontal action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FlipHorizontalAction is triggered.
     * It redoes the most recently undone operation.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new FlipHorizontal());
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
   * Action to flip vertical a buffered image
   * </p>
   *
   * see flipvertical()
   */
  public class FlipVerticalAction extends ImageAction {

    /**
     * <p>
     * Create a new flip vertical action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FlipVerticalAction(String name, ImageIcon icon, String desc, int mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the flip vertical action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the FlipVerticalAction is triggered.
     * It redoes the most recently undone operation.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().apply(new FlipVertical());
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
   * Action to resize a Buffered Image
   * </p>
   *
   * see resize()
   */
  public class ResizeAction extends ImageAction {

    /**
     * <p>
     * Create a new resize action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ResizeAction(String name, ImageIcon icon, String desc, int mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the image resize action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ResizeAction is triggered.
     * It resizes the current image.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        int size = 100;

        JPanel sliderPanel = createSliderPanel();
        int option = JOptionPane.showOptionDialog(
          null,
          sliderPanel,
          Andie.bundle.getString("resize_menu_title"),
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          null,
          null
        );

        if (option == JOptionPane.CANCEL_OPTION) {
          return;
        } else if (option == JOptionPane.OK_OPTION) {
          JSlider sizeSlider = (JSlider) sliderPanel.getComponent(0);
          size = sizeSlider.getValue();
        }

        target.getImage().apply(new Resize(size));
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

    /**
     * <p>
     * This method creates a JPanel containing a JSlider and a JLabel.
     * The label informs the user of the currently selected value in the slider.
     * </p>
     *
     * @return sliderPanel
     */
    private JPanel createSliderPanel() {
      JSlider slider = new JSlider(25, 175);
      slider.setMajorTickSpacing(25);
      slider.setMinorTickSpacing(5);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setValue(100);
      slider.setPreferredSize(new Dimension(300, 50));
      String currentSelectionText =
        Andie.bundle.getString("resize_menu_current_selection_text") + ":  ";

      JLabel valueLabel = new JLabel(
        currentSelectionText + Integer.toString(slider.getValue()) + "%"
      );
      slider.addChangeListener((ChangeEvent e) -> {
        JSlider source = (JSlider) e.getSource();
        valueLabel.setText(
          currentSelectionText + Integer.toString(source.getValue()) + "%"
        );
      });

      JPanel sliderPanel = new JPanel(new BorderLayout());
      valueLabel.setBorder(BorderFactory.createEmptyBorder(20, 60, 10, 20));
      slider.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
      sliderPanel.add(slider, BorderLayout.CENTER);
      sliderPanel.add(valueLabel, BorderLayout.SOUTH);

      return sliderPanel;
    }
  }
}
