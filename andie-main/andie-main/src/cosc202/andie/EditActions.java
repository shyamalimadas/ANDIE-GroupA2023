package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 *
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class EditActions {

  /** A list of actions for the Edit menu. */
  protected ArrayList<Action> actions;
  private CropAction cropAction;
  private DrawAction drawAction;

  /**
   * <p>
   * Create a set of Edit menu actions.
   * </p>
   */
  public EditActions() {
    actions = new ArrayList<Action>();
    actions.add(
      new UndoAction(
        Andie.bundle.getString("undo_title"),
        null,
        Andie.bundle.getString("undo_desc"),
        Integer.valueOf(KeyEvent.VK_Z)
      )
    );
    actions.add(
      new RedoAction(
        Andie.bundle.getString("redo_title"),
        null,
        Andie.bundle.getString("redo_desc"),
        Integer.valueOf(KeyEvent.VK_Y)
      )
    );
    cropAction = new CropAction();
    actions.add(cropAction);
    drawAction = new DrawAction();
    actions.add(drawAction);
  }

  public Action getDraw() {
    return drawAction;
  }

  /**
   * <p>
   * Get the crop action.
   * </p>
   *
   * @return The crop action.
   */
  public Action getCrop() {
    return cropAction;
  }

  /**
   * <p>
   * Get the undo action.
   * </p>
   *
   * @return The undo action.
   */
  public Action getUndo() {
    return actions.get(0);
  }

  /**
   * <p>
   * Get the redo action.
   * </p>
   *
   * @return The redo action.
   */
  public Action getRedo() {
    return actions.get(1);
  }

  /**
   * <p>
   * Create a menu contianing the list of Edit actions with submenu for draw
   * actions.
   * </p>
   *
   * @return The edit menu UI element.
   */
  public JMenu createMenu() {
    JMenu editMenu = new JMenu(Andie.bundle.getString("edit_title"));
    JMenu drawSubMenu = new JMenu(Andie.bundle.getString("draw_title"));

    Map<String, String> shape = new HashMap<>();
    shape.put("Rectangle", Andie.bundle.getString("rectangle_title"));
    shape.put("Oval", Andie.bundle.getString("oval_title"));
    shape.put("Line", Andie.bundle.getString("line_title"));
    shape.put(
      "Rectangle Outline",
      Andie.bundle.getString("rectangle_outline_title")
    );
    shape.put("Oval Outline", Andie.bundle.getString("oval_outline_title"));

    Map<String, String> color = new HashMap<>();
    color.put("Red", Andie.bundle.getString("red_title"));
    color.put("Green", Andie.bundle.getString("green_title"));
    color.put("Blue", Andie.bundle.getString("blue_title"));
    color.put("Yellow", Andie.bundle.getString("yellow_title"));
    color.put("Purple", Andie.bundle.getString("purple_title"));

    for (Map.Entry<String, String> shapeEntry : shape.entrySet()) {
      JMenu shapeSubMenu = new JMenu(shapeEntry.getValue());

      for (Map.Entry<String, String> colorEntry : color.entrySet()) {
        shapeSubMenu.add(
          new JMenuItem(
            new AbstractAction(colorEntry.getValue()) {
              @Override
              public void actionPerformed(ActionEvent e) {
                drawAction.setShape(shapeEntry.getKey());
                drawAction.setColor(colorEntry.getKey());
                drawAction.actionPerformed(e);
              }
            }
          )
        );
      }
      drawSubMenu.add(shapeSubMenu);
    }

    // Add all actions to the menu
    for (Action action : actions) {
      if (action == drawAction) editMenu.add(drawSubMenu); else editMenu.add(
        new JMenuItem(action)
      );
    }

    return editMenu;
  }

  /**
   * <p>
   * Action to undo an {@link ImageOperation}.
   * </p>
   *
   * @see EditableImage#undo()
   */
  public class UndoAction extends ImageAction {

    /**
     * * Create a new undo action.
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the undo action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the UndoAction is triggered.
     * It undoes the most recently applied operation.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().undo();
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
   * Action to redo an {@link ImageOperation}.
   * </p>
   *
   * @see EditableImage#redo()
   */
  public class RedoAction extends ImageAction {

    /**
     * <p>
     * Create a new redo action.
     * </p>
     *
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * <p>
     * Callback for when the redo action is triggered.
     * </p>
     *
     * <p>
     * This method is called whenever the RedoAction is triggered.
     * It redoes the most recently undone operation.
     * </p>
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        target.getImage().redo();
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
   * Action to create a {@link Crop} operation on an image.
   * </p>
   *
   * <p>
   * CropAction is a specialized {@link ImageAction} which enables the cropping of
   * images within the image editing tool. This action is triggered when the user
   * selects a region of an image on the {@link ImagePanel} and then executes the
   * crop
   * action. The action crops the image to the selected area. If no valid
   * selection
   * exists, it displays an error message.
   * </p>
   *
   * @see EditableImage#apply(ImageOperation)
   */

  public class CropAction extends ImageAction {

    /**
     * Constructs a new CropAction.
     */
    public CropAction() {
      super(
        Andie.bundle.getString("crop_title"),
        null,
        Andie.bundle.getString("crop_desc"),
        KeyEvent.VK_C
      );
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)
        );
    }

    /**
     * Executes the crop action. If a valid selection exists on the target
     * ImagePanel, it crops the image to the selected area. If no valid selection
     * exists, it shows
     * an error message.
     *
     *
     * @param e The ActionEvent that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        ImagePanel target = getTarget();
        if (target != null) {
          Rectangle selection = target.getSelection();
          if (
            selection != null && selection.width > 0 && selection.height > 0
          ) {
            Crop cropOp = new Crop(selection);
            target.getImage().apply(cropOp);
            target.setSelection(new Rectangle());
            target.repaint();
          } else {
            try {
              Andie.createErrorMessage(
                Andie.bundle.getString("selection_crop_error")
              );
            } catch (Exception ex) {
              ex.printStackTrace();
              System.exit(1);
            }
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
   * <p>
   * Action to create a {@link Draw} operation on an image.
   * </p>
   *
   * <p>
   * DrawAction is a specialized {@link ImageAction} that allows the user to draw
   * selected
   * shapes on an image. Users can select a region of an image on the
   * {@link ImagePanel}
   * and then execute the draw action. It draws the specified shape in the
   * selected area.
   * If no valid selection exists, it displays an error message.
   * </p>
   *
   *
   */

  private class DrawAction extends ImageAction {

    private String shape;
    private String color;

    public DrawAction() {
      super(
        Andie.bundle.getString("draw_title"),
        null,
        Andie.bundle.getString("draw_desc"),
        KeyEvent.VK_SPACE
      );
    }

    public void setShape(String shape) {
      this.shape = shape;
    }

    public void setColor(String color) {
      this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (ImagePanel.image.hasImage()) {
        ImagePanel target = getTarget();
        if (target != null) {
          Rectangle selection = target.getSelection();
          if (
            selection != null && selection.width > 0 && selection.height > 0
          ) {
            Draw drawOp = new Draw(target, shape, color);
            target.getImage().apply(drawOp);
            target.setSelection(new Rectangle());
            target.repaint();
          } else {
            try {
              Andie.createErrorMessage(
                Andie.bundle.getString("selection_draw_error")
              );
            } catch (Exception ex) {
              ex.printStackTrace();
              System.exit(1);
            }
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
