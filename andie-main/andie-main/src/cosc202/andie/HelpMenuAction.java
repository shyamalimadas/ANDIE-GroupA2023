package cosc202.andie;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class HelpMenuAction {

  /** A list of actions for the Help menu. */
  protected ArrayList<Action> actions;

  public HelpMenuAction() {
    actions = new ArrayList<Action>();
    actions.add(
      new FileHelpAction(
        Andie.bundle.getString("help_file_actions_title"),
        null,
        Andie.bundle.getString("help_file_actions_desc"),
        Integer.valueOf(KeyEvent.VK_W)
      )
    );
    actions.add(
      new MacroHelpAction(
        Andie.bundle.getString("help_macro_actions_title"),
        null,
        Andie.bundle.getString("help_macro_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F2)
      )
    );
    actions.add(
      new EditHelpAction(
        Andie.bundle.getString("help_edit_actions_title"),
        null,
        Andie.bundle.getString("help_edit_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F3)
      )
    );
    actions.add(
      new ViewHelpActions(
        Andie.bundle.getString("help_view_actions_title"),
        null,
        Andie.bundle.getString("help_view_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F4)
      )
    );
    actions.add(
      new TransformHelpActions(
        Andie.bundle.getString("help_transform_actions_title"),
        null,
        Andie.bundle.getString("help_transform_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F5)
      )
    );
    actions.add(
      new FilterHelpActions(
        Andie.bundle.getString("help_filter_actions_title"),
        null,
        Andie.bundle.getString("help_filter_actions_desc"),
        Integer.valueOf(KeyEvent.VK_R)
      )
    );
    actions.add(
      new ColourHelpActions(
        Andie.bundle.getString("help_colour_actions_title"),
        null,
        Andie.bundle.getString("help_colour_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F6)
      )
    );
    actions.add(
      new StickerHelpAction(
        Andie.bundle.getString("help_sticker_actions_title"),
        null,
        Andie.bundle.getString("help_sticker_actions_desc"),
        Integer.valueOf(KeyEvent.VK_F7)
      )
    );
    actions.add(
      new PreferencesHelpActions(
        Andie.bundle.getString("help_preferences_actions_title"),
        null,
        Andie.bundle.getString("help_preferences_actions_desc"),
        Integer.valueOf(KeyEvent.VK_SEMICOLON)
      )
    );
  }

  /**
   * <p>
   * Create a menu contianing the list of Help actions.
   * </p>
   *
   * @return The Help menu UI element.
   */
  public JMenu createMenu() {
    JMenu helpMenu = new JMenu(Andie.bundle.getString("help_menu_title"));
    for (Action action : actions) {
      helpMenu.add(new JMenuItem(action));
    }
    return helpMenu;
  }

  // File Help Action
  private class FileHelpAction extends ImageAction {

    FileHelpAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_file_actions_menu_desc"),
        Andie.bundle.getString("help_file_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Macro Help Action
  private class MacroHelpAction extends ImageAction {

    public MacroHelpAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_macro_actions_menu_desc"),
        Andie.bundle.getString("help_macro_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Edit Help Action
  private class EditHelpAction extends ImageAction {

    public EditHelpAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_edit_actions_menu_desc"),
        Andie.bundle.getString("help_edit_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // View Help Action
  private class ViewHelpActions extends ImageAction {

    public ViewHelpActions(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_view_actions_menu_desc"),
        Andie.bundle.getString("help_view_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Transform Help Action
  private class TransformHelpActions extends ImageAction {

    public TransformHelpActions(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_transform_actions_menu_desc"),
        Andie.bundle.getString("help_transform_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  private class FilterHelpActions extends ImageAction {

    public FilterHelpActions(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_filter_actions_menu_desc"),
        Andie.bundle.getString("help_filter_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Colour Help Action
  private class ColourHelpActions extends ImageAction {

    public ColourHelpActions(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.CTRL_DOWN_MASK)
        );
    }

    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_colour_actions_menu_desc"),
        Andie.bundle.getString("help_colour_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Sticket Help Action
  private class StickerHelpAction extends ImageAction {

    public StickerHelpAction(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(KeyEvent.VK_F7, InputEvent.CTRL_DOWN_MASK)
        );
    }

    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_sticker_actions_menu_desc"),
        Andie.bundle.getString("help_sticker_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }

  // Preferences Help Action
  private class PreferencesHelpActions extends ImageAction {

    public PreferencesHelpActions(
      String name,
      ImageIcon icon,
      String desc,
      Integer mnemonic
    ) {
      super(name, icon, desc, mnemonic);
      this.putValue(
          Action.ACCELERATOR_KEY,
          KeyStroke.getKeyStroke(
            KeyEvent.VK_SEMICOLON,
            InputEvent.CTRL_DOWN_MASK
          )
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(
        null,
        Andie.bundle.getString("help_preferences_actions_menu_desc"),
        Andie.bundle.getString("help_preferences_actions_menu_title"),
        JOptionPane.INFORMATION_MESSAGE
      );
    }
  }
}
