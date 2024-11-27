package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Draw implements ImageOperation, java.io.Serializable {
    private ImagePanel imagePanel;
    private String shape;
    private String color;
    private double scale;

    public Draw() {
    }

    /**
     * <p>
     * Constructor for Draw class
     * 
     * @param imagePanel the image panel on which the shape is being drawn
     * @param shape      string representation of the shape being drawn
     * @param color      string representation of the color of the shape being drawn
     *                   <p>
     */

    public Draw(ImagePanel imagePanel, String shape, String color) {
        this.imagePanel = imagePanel;
        this.shape = shape;
        this.color = color;
        this.scale = this.imagePanel.getScale();
    }

    /**
     * <p>
     * Draws a shape whos color and geometry is determined by the values of the
     * instance variables
     * 
     * @param input buffered image on which the shape is being drawn
     *              <p>
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        switch (color) {
            case "Red":
                g2d.setColor(Color.RED);
                break;

            case "Green":
                g2d.setColor(Color.GREEN);
                break;

            case "Blue":
                g2d.setColor(Color.BLUE);
                break;

            case "Orange":
                g2d.setColor(Color.ORANGE);
                break;

            case "Yellow":
                g2d.setColor(Color.YELLOW);
                break;

            case "Purple":
                g2d.setColor(Color.MAGENTA);
                break;
        }

        Rectangle selection = imagePanel.getSelection();
        int x = (int) (selection.x / scale); // Scaled x coordinate of left corner of selection rectangle.
        int y = (int) (selection.y / scale); // Sclaed y coordinate of top right corner of selection rectangle.
        int w = (int) (selection.width / scale); // Scaled width of selection rectangle
        int h = (int) (selection.height / scale); // Scaled height of selection rectangle
        Point startPoint = imagePanel.getStartPoint();
        int ox = (int) (startPoint.x / scale); // Scaled x coordinate of start cursor position
        int oy = (int) (startPoint.y / scale); // Scaled y coordinate of start cursor position
        Point endpoint = imagePanel.getEndPoint();
        int fx = (int) (endpoint.x / scale); // Scaled x coordinate of final cursor position
        int fy = (int) (endpoint.y / scale); // Scaled y coordinate of final cursor position

        switch (shape) {
            case "Rectangle":
                g2d.fillRect(x, y, w, h);
                break;

            case "Oval":
                g2d.fillOval(x, y, w, h);
                break;

            case "Line":
                g2d.drawLine(ox, oy, fx, fy);
                break;

            case "Rectangle Outline":
                g2d.drawRect(x, y, w, h);
                break;

            case "Oval Outline":
                g2d.drawOval(x, y, w, h);
                break;
        }
        return input;
    }
}
