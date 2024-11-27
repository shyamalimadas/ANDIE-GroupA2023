package cosc202.andie;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
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
public class ImagePanel extends JPanel {

    /**
     * A Rectangle object to represent the selected area on the image.
     */
    private Rectangle selection;

    /**
     * A Point object to represent final position of the mouse when selecting a
     * region.
     */
    private Point endPoint;

    /**
     * A Point object to represent the starting position of the mouse when selecting
     * a region.
     */
    private Point startPoint;

    /**
     * The image to display in the ImagePanel.
     */
    public static EditableImage image;
    protected static MouseAdapter mouseHandler;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale = 1.0;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */

    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;

        /**
         * Initializing the Rectangle object.
         */
        selection = new Rectangle();

        /**
         * A MouseAdapter object to handle mouse events for selecting a region on the
         * image.
         * On mouse press, it records the starting point of the selection,
         * and on mouse drag, it updates the bounds of the selection rectangle.
         */
        mouseHandler = new MouseAdapter() {
            /**
             * A Point object to record the starting point of the selection.
             */

            /**
             * The mousePressed method records the point where the mouse was pressed,
             * which is the starting point of the selection.
             *
             * @param e The MouseEvent that occurred.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (ImagePanel.image.hasImage()) {
                    if (!Stickers.stickerMode) {
                        startPoint = e.getPoint();
                        if (isWithinImageBounds(startPoint)) {
                            selection.setBounds((int) (startPoint.x / scale), (int) (startPoint.y / scale), 0, 0);
                            repaint();
                        } else {
                            startPoint = null;
                        }
                    }
                }
            }

            private boolean isWithinImageBounds(Point point) {
                int x = point.x;
                int y = point.y;
                int imageWidth = (int) (image.getCurrentImage().getWidth() * scale);
                int imageHeight = (int) (image.getCurrentImage().getHeight() * scale);

                return x >= 0 && x <= imageWidth && y >= 0 && y <= imageHeight;
            }

            /**
             * The mouseDragged method updates the bounds of the selection rectangle
             * as the mouse is dragged.
             *
             * @param e The MouseEvent that occurred.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ImagePanel.image.hasImage()) {
                    if (!Stickers.stickerMode) {
                        endPoint = e.getPoint();

                        // Ensure the endPoint is within the bounds of the image
                        endPoint.x = (int) Math.min(image.getCurrentImage().getWidth() * scale,
                                Math.max(0, endPoint.x));
                        endPoint.y = (int) Math.min(image.getCurrentImage().getHeight() * scale,
                                Math.max(0, endPoint.y));

                        selection.setBounds(
                                (int) Math.min(startPoint.x / scale, endPoint.x / scale),
                                (int) Math.min(startPoint.y / scale, endPoint.y / scale),
                                (int) Math.abs((startPoint.x - endPoint.x) / scale),
                                (int) Math.abs((startPoint.y - endPoint.y) / scale));

                        repaint();
                    }
                }
            }
        };

        /**
         * Adding the MouseAdapter as a listener for mouse events on this ImagePanel.
         */
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * The paintComponent method draws the current image and the selection rectangle
     * (if any).
     *
     * @param g The Graphics object to protect the original Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);

            /**
             * If a selection exists, it is drawn on the image.
             */
            if (selection != null) {
                g2.setColor(new Color(192, 192, 192, 128)); // Semi-transparent gray
                g2.fill(selection);
                g2.setColor(Color.WHITE);
                g2.draw(selection);
            }

            g2.dispose();
        }
    }

    /**
     * The getSelection method returns the current selection.
     *
     * @return The current selection as a Rectangle object.
     */
    public Rectangle getSelection() {
        return this.selection;
    }

    /**
     * The getStartPoint method returns the current StartPoint.
     *
     * @return The current StartPoint.
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * The getEndPoint method returns the current EndPoint.
     *
     * @return The current endPoint
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * <p>
     * setSelection method to update the selected region on the image
     * </p>
     * 
     * @param selection
     */
    public void setSelection(Rectangle selection) {
        this.selection = selection;
        repaint();
    }

    /**
     * <p>
     * Get the current scale as a decimal multiplier.
     * </p>
     * 
     * <p>
     * The scale is used for internal calculations, where 1.0 is the
     * original size, 0.5 is half-size, etc.
     * </p>
     * 
     * @return The current scale as a decimal multiplier.
     */

    public double getScale() {
        return scale;
    }

}