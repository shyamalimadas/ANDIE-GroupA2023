package cosc202.andie;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 *
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    protected ArrayList<Action> embossActions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction(Andie.bundle.getString("mean_filter_title"), null,
                Andie.bundle.getString("mean_filter_desc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction(Andie.bundle.getString("sharpen_filter_title"), null,
                Andie.bundle.getString("sharpen_filter_desc"),
                Integer.valueOf(KeyEvent.VK_P)));
        actions.add(
                new MedianFilterAction(Andie.bundle.getString("median_filter_title"), null,
                        Andie.bundle.getString("median_filter_desc"), Integer.valueOf(KeyEvent.VK_D)));
        actions.add(new GuassianFilterAction(Andie.bundle.getString("gaussian_filter_title"), null,
                Andie.bundle.getString("gaussian_filter_desc"),
                Integer.valueOf(KeyEvent.VK_U)));
        actions.add(new NegativeFilterAction(Andie.bundle.getString("negative_filter_title"), null,
                Andie.bundle.getString("negative_filter_desc"),
                Integer.valueOf(KeyEvent.VK_N)));
        embossActions = new ArrayList<Action>();
        embossActions.add(new EmbossFilter1Action(Andie.bundle.getString("emboss_1_title"), null,
                Andie.bundle.getString("emboss_1_desc"),
                Integer.valueOf(KeyEvent.VK_1)));
        embossActions.add(new EmbossFilter2Action(Andie.bundle.getString("emboss_2_title"), null,
                Andie.bundle.getString("emboss_2_desc"),
                Integer.valueOf(KeyEvent.VK_2)));
        embossActions.add(new EmbossFilter3Action(Andie.bundle.getString("emboss_3_title"), null,
                Andie.bundle.getString("emboss_3_desc"),
                Integer.valueOf(KeyEvent.VK_3)));
        embossActions.add(new EmbossFilter4Action(Andie.bundle.getString("emboss_4_title"), null,
                Andie.bundle.getString("emboss_4_desc"),
                Integer.valueOf(KeyEvent.VK_4)));
        embossActions.add(new EmbossFilter5Action(Andie.bundle.getString("emboss_5_title"), null,
                Andie.bundle.getString("emboss_5_desc"),
                Integer.valueOf(KeyEvent.VK_5)));
        embossActions.add(new EmbossFilter6Action(Andie.bundle.getString("emboss_6_title"), null,
                Andie.bundle.getString("emboss_6_desc"),
                Integer.valueOf(KeyEvent.VK_6)));
        embossActions.add(new EmbossFilter7Action(Andie.bundle.getString("emboss_7_title"), null,
                Andie.bundle.getString("emboss_7_desc"),
                Integer.valueOf(KeyEvent.VK_7)));
        embossActions.add(new EmbossFilter8Action(Andie.bundle.getString("emboss_8_title"), null,
                Andie.bundle.getString("emboss_8_desc"),
                Integer.valueOf(KeyEvent.VK_8)));
        embossActions.add(new SobelHorizontalAction(Andie.bundle.getString("sobel_horizontal_title"), null,
                Andie.bundle.getString("sobel_horizontal_desc"),
                Integer.valueOf(KeyEvent.VK_9)));
        embossActions.add(new SobelVerticalAction(Andie.bundle.getString("sobel_vertical_title"), null,
                Andie.bundle.getString("sobel_vertical_desc"),
                Integer.valueOf(KeyEvent.VK_0)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     *
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("filter_title"));
        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        JMenu embossMenu = new JMenu(Andie.bundle.getString("emboss_filter_title"));
        for (Action action : embossActions) {
            embossMenu.add(new JMenuItem(action));
        }

        fileMenu.add(embossMenu);

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_DOLLAR, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                JSlider radiusModel = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
                radiusModel.setMajorTickSpacing(1);
                radiusModel.setMinorTickSpacing(1);
                radiusModel.setPaintTicks(true);
                radiusModel.setPaintLabels(true);
                int option = JOptionPane.showOptionDialog(null, radiusModel,
                        Andie.bundle.getString("mean_filter_menu_title"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getValue();
                }

                // Create and apply the mean filter
                target.getImage().apply(new MeanFilter(radius));
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
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * {@link SharpenFilter}
     *
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                // Pop-up dialog box to ask for the radius value.
                JSlider radiusModel = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
                radiusModel.setMajorTickSpacing(1);
                radiusModel.setMinorTickSpacing(1);
                radiusModel.setPaintTicks(true);
                radiusModel.setPaintLabels(true);
                int option = JOptionPane.showOptionDialog(
                        null,
                        radiusModel,
                        Andie.bundle.getString("sharpen_filter_menu_title"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                // Create and apply the sharpen filter

                target.getImage().apply(new SharpenFilter());
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
     * Action to blur an image with median filter.
     * </p>
     *
     * @see MedianFilter
     *
     */

    public class MedianFilterAction extends ImageAction {

        MedianFilterAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                // Determine the radius - ask the user.
                int radius = 1;
                JSlider radiusModel = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
                radiusModel.setMajorTickSpacing(1);
                radiusModel.setMinorTickSpacing(1);
                radiusModel.setPaintTicks(true);
                radiusModel.setPaintLabels(true);
                int option = JOptionPane.showOptionDialog(null, radiusModel,
                        Andie.bundle.getString("median_filter_menu_title"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getValue();
                }

                JDialog frame = new JDialog();

                frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                JLabel label = new JLabel("Loading...");

                frame.add(label);

                frame.pack();
                frame.setLocationRelativeTo(null);
                // Set the frame visible
                frame.setVisible(true);

                int actualRadius = radius;

                SwingUtilities.invokeLater(() -> {
                    // Create and apply the filter
                    target.getImage().apply(new MedianFilter(actualRadius));
                    target.repaint();
                    target.getParent().revalidate();

                    frame.setVisible(false);
                    frame.dispose();
                });
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
     * Action to blur an image with a guassian filter.
     * </p>
     *
     * @see GaussianFilter
     */
    public class GuassianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new guassian-filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GuassianFilterAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the guassian-filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the GuassianFilterAction is triggered.
         * It prompts the user for a filter radius and variance, then applys an
         * appropriately sized
         * {@link GaussianFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                JSlider radiusModel = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
                radiusModel.setMajorTickSpacing(1);
                radiusModel.setMinorTickSpacing(1);
                radiusModel.setPaintTicks(true);
                radiusModel.setPaintLabels(true);

                int option = JOptionPane.showOptionDialog(
                        null,
                        radiusModel,
                        Andie.bundle.getString("gaussian_filter_menu_title"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getValue();
                }
                JDialog frame = new JDialog();

                frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                JLabel label = new JLabel("Loading...");

                frame.add(label);

                frame.pack();
                frame.setLocationRelativeTo(null);
                // Set the frame visible
                frame.setVisible(true);

                int actualRadius = radius;

                SwingUtilities.invokeLater(() -> {
                    // Create and apply the filter
                    target.getImage().apply(new GaussianFilter(actualRadius));
                    target.repaint();
                    target.getParent().revalidate();

                    frame.setVisible(false);
                    frame.dispose();
                });
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
     * Action to apply emboss from the direction of top left to bottom right.
     * </p>
     *
     * @see EmbossFilter1
     */
    public class EmbossFilter1Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter1Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from top left action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter1 is triggered.
         * It applies the emboss simulation starting from top left of the image.
         * {@link EmbossFilter1}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter1());
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
     * Action to apply emboss from the direction of mid left.
     * </p>
     *
     * @see EmbossFilter2
     */
    public class EmbossFilter2Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter2Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from mid left action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter2 is triggered.
         * It applies the emboss simulation starting from mid left of the image.
         * {@link EmbossFilter2}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter2());
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
     * Action to apply emboss from the direction of top mid.
     * </p>
     *
     * @see EmbossFilter3
     */
    public class EmbossFilter3Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter3Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from top mid action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter3 is triggered.
         * It applies the emboss simulation starting from top mid of the image.
         * {@link EmbossFilter3}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter3());
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
     * Action to apply emboss from the direction of top right to bottom left.
     * </p>
     *
     * @see EmbossFilter4
     */
    public class EmbossFilter4Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter4Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from top right action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter4 is triggered.
         * It applies the emboss simulation starting from top right of the image.
         * {@link EmbossFilter4}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter4());
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
     * Action to apply emboss from the direction of mid right.
     * </p>
     *
     * @see EmbossFilter5
     */
    public class EmbossFilter5Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter5Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from mid right action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter5 is triggered.
         * It applies the emboss simulation starting from mid right of the image.
         * {@link EmbossFilter5}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter5());
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
     * Action to apply emboss from the direction of bottom right to top left.
     * </p>
     *
     * @see EmbossFilter6
     */
    public class EmbossFilter6Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter6Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from bottom right action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter6 is triggered.
         * It applies the emboss simulation starting from bottom right of the image.
         * {@link EmbossFilter6}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter6());
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
     * Action to apply emboss from the direction of bottom middle.
     * </p>
     *
     * @see EmbossFilter1
     */
    public class EmbossFilter7Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter7Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from bottom middle action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter7 is triggered.
         * It applies the emboss simulation starting from bottom middle of the image.
         * {@link EmbossFilter7}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter7());
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
     * Action to apply emboss from the direction of bottom left to top right.
     * </p>
     *
     * @see EmbossFilter8
     */
    public class EmbossFilter8Action extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilter8Action(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when emboss the image from bottom left action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the EmbossFilter8 is triggered.
         * It applies the emboss simulation starting from bottom left of the image.
         * {@link EmbossFilter8}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new EmbossFilter8());
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
     * Action to apply sobel filter from the direction of horizontal.
     * </p>
     *
     * @see SobelHorizontal
     */
    public class SobelHorizontalAction extends ImageAction {

        /**
         * <p>
         * Create a new horizontal sobel action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelHorizontalAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when sobel the image from horizontal action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the SobelHorizontal is triggered.
         * It applies the sobel simulation.
         * {@link SobelHorizontal}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new SobelHorizontal());
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
     * Action to apply sobel filter from the direction of vertical.
     * </p>
     *
     * @see SobelVertical
     */
    public class SobelVerticalAction extends ImageAction {

        /**
         * <p>
         * Create a new vertical sobel action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelVerticalAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, 0));
        }

        /**
         * <p>
         * Callback for when sobel the image from horizontal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelVertical is triggered.
         * It applies the sobel simulation.
         * {@link SobelVertical}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new SobelVertical());
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
     * @see NegativeFilter
     */
    public class NegativeFilterAction extends ImageAction {

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
        NegativeFilterAction(
                String name,
                ImageIcon icon,
                String desc,
                Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                // Pop-up dialog box to ask for the specified brightness value.
                JSlider negativeSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 0);
                negativeSlider.setMajorTickSpacing(50);
                negativeSlider.setMinorTickSpacing(10);
                negativeSlider.setPaintTicks(true);
                negativeSlider.setPaintLabels(true);

                int option = JOptionPane.showOptionDialog(
                        null,
                        negativeSlider,
                        Andie.bundle.getString("negative_filter_menu_title"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    int negative = negativeSlider.getValue();

                    // Create and apply the filter
                    target.getImage().apply(new NegativeFilter(negative));
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
