package cosc202.andie;

import java.awt.image.*;
import java.awt.Graphics2D;

/**
 * <p>
 * ImageOperation to resize an image.
 * </p>
 * 
 * <p>
 * The image produced by this operation is resized given a percentage.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Josh Lawson
 * @version 1.0
 */
public class Resize implements ImageOperation, java.io.Serializable {
    /**
     * The percentage of resize to apply.
     */
    public int size;

    /**
     * <p>
     * Construct a resized image with the given percentage.
     * </p>
     * 
     * @param size The percentage of the newly resized image
     */
    public Resize(int size) {
        this.size = size;
    }

    /**
     * <p>
     * Apply resize transformation to an image.
     * </p>
     * 
     * @param input The image to be resized
     * @return The resulting resized image.
     */
    public BufferedImage apply(BufferedImage input) {
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int targetWidth = inputWidth * size / 100;
        int targetHeight = inputHeight * size / 100;

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(input, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }

}