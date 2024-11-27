package cosc202.andie;

import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 *
 * <p>
 * The View menu contains actions that affect how the image is displayed in the
 * application.
 * These actions do not affect the contents of the image itself, just the way it
 * is displayed.
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
public class ViewActions {

  /**
   * A list of actions for the View menu.
   */
  protected ArrayList<Action> actions;

  /**
   * <p>
   * Create a set of View menu actions.
   * </p>
   */

  // WIP: Keybinds for certain actions still need to be discussed.

  public ViewActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new ZoomInAction(
        Andie.bundle.getString("zoom_in_title"),
        null,
        Andie.bundle.getString("zoom_in_desc"),
        Integer.valueOf(KeyEvent.VK_PLUS)
      )
    );
    actions.add(
      new ZoomOutAction(
        Andie.bundle.getString("zoom_out_title"),
        null,
        Andie.bundle.getString("zoom_out_desc"),
        Integer.valueOf(KeyEvent.VK_MINUS)
      )
    );
    actions.add(
      new ZoomFullAction(
        Andie.bundle.getString("zoom_full_title"),
        null,
        Andie.bundle.getString("zoom_full_desc"),
        Integer.valueOf(KeyEvent.VK_BACK_SLASH)
      )
    );
  }

  /**
   * <p>
   * Create a menu containing the list of View actions.
   * </p>
   *
   * @return The view menu UI element.
   */
  public JMenu createMenu() {
    JMenu viewMenu = new JMenu(Andie.bundle.getString("view_title"));
    for (Action action : actions) {
      viewMenu.add(new JMenuItem(action));
    }

    return viewMenu;
  }

  /**
   * <p>
   * Get the zoom in action.
   * </p>
   *
   * @return The zoom in action.
   */
  public Action getZoomIn() {
    return actions.get(0);
  }

  /**
   * <p>
   * Get the zoom out action.
   * </p>
   *
   * @return The zoom out action.
   */
  public Action getZoomOut() {
    return actions.get(1);
  }

  /**
   * <p>
   * Get the zoom full action.
   * </p>
   *
   * @return The zoom full action.
   */
  public Action getZoomFull() {
    return actions.get(2);
  }

  /**
   * <p>
   * Action to zoom in on an image.
   * </p>
   *
   * <p>
   * Note that this action only affects the way the image is displayed, not its
   * actual contents.
   * </p>
   */
  public class ZoomInAction extends ImageAction {

    /**
     * <p>
     * Create a new zoom-in action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the zoom-in action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ZoomInAction is triggered.
     * It increases the zoom level by 10%, to a maximum of 200%.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.setZoom(target.getZoom() + 10);
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
   * Action to zoom out of an image.
   * </p>
   *
   * <p>
   * Note that this action only affects the way the image is displayed, not its
   * actual contents.
   * </p>
   */
  public class ZoomOutAction extends ImageAction {

    /**
     * <p>
     * Create a new zoom-out action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the zoom-out action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ZoomOutAction is triggered.
     * It decreases the zoom level by 10%, to a minimum of 50%.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.setZoom(target.getZoom() - 10);
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
   * Action to reset the zoom level to actual size.
   * </p>
   *
   * <p>
   * Note that this action only affects the way the image is displayed, not its
   * actual contents.
   * </p>
   */
  public class ZoomFullAction extends ImageAction {

    /**
     * <p>
     * Create a new zoom-full action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(
            KeyEvent.VK_BACK_SLASH,
            InputEvent.CTRL_DOWN_MASK
          )
        );
    }

    /**
     * <p>
     * Callback for when the zoom-full action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the ZoomFullAction is triggered.
     * It resets the Zoom level to 100%.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.setZoom(100);
        target.revalidate();
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
}
