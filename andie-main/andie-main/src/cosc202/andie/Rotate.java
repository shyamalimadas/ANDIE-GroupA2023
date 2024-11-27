package cosc202.andie;

import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * <p>
 * ImageOperation to rotate an image.
 * </p>
 * 
 * <p>
 * The image produced by this operation is rotated by the rotation angle.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Ivan Chevtchenko
 */
public class Rotate implements ImageOperation, java.io.Serializable {
    // The angle to rotate the image by.
    private int rotationAngle = 0;

    /**
     * <p>
     * Create a new Rotate operation.
     * </p>
     * 
     * @param rotationAngle The angle to rotate the image by.
     */
    public Rotate(int rotationAngle) {
        if (this.rotationAngle == 270) {

            if (rotationAngle == 270) {
                this.rotationAngle = 180;
            } else if (rotationAngle == 180) {
                this.rotationAngle = 90;
            } else if (rotationAngle == 90) {
                this.rotationAngle = 0;
            }

        } else {
            this.rotationAngle += rotationAngle;
        }

    }

    /**
     * <p>
     * Apply rotate transformation to an image.
     * </p>
     * 
     * @param input The image to be rotated.
     * @return The resulting rotated image.
     */
    public BufferedImage apply(BufferedImage input) {

        double radians = Math.toRadians(this.rotationAngle);
        int width = input.getWidth();
        int height = input.getHeight();
        int newWidth = (int) Math.round(width * Math.abs(Math.cos(radians)) + height * Math.abs(Math.sin(radians)));
        int newHeight = (int) Math.round(width * Math.abs(Math.sin(radians)) + height * Math.abs(Math.cos(radians)));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, input.getType());
        Graphics2D g = rotatedImage.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - width) / 2, (newHeight - height) / 2);
        at.rotate(radians, width / 2, height / 2);

        g.setTransform(at);
        g.drawImage(input, 0, 0, null);
        g.dispose();

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        input = op.filter(input, null);

        return input;
    }

    /**
     * <p>
     * Returns the rotation angle
     * </p>
     * 
     * @return The rotation angle.
     */
    public int getRotationAngle() {
        return rotationAngle;
    }

}