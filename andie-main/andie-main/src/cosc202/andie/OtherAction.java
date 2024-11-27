package cosc202.andie;

import javax.swing.*;

/**
 * <p>
 * Abstract class representing actions the user might take in the interface that does not
 * apply to images.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public abstract class OtherAction extends AbstractAction {
   
    /**
     * <p>
     * Constructor for OtherActions.
     * </p>
     * 
     * <p>
     * To construct an Action you provide the information needed to integrate it with the interface.
     * This is for actions that do not apply to images.
     * Note that the target is not specified per-action, but via the static member {@link target}
     * via {@link setTarget}.
     * </p>
     * 
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action  (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
     */
    OtherAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon);
        if (desc != null) {
           putValue(SHORT_DESCRIPTION, desc);
        }
        if (mnemonic != null) {
            putValue(MNEMONIC_KEY, mnemonic);
        }
    }

}
