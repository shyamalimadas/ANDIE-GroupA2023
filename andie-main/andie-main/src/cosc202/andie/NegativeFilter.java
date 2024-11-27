package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply negative operation to an image.
 * </p>
 * 
 * <p>
 * A negative image is a total inversion, in which light areas appear dark and
 * vice versa.
 * A negative color image is additionally color-reversed, with red areas
 * appearing cyan,
 * greens appearing magenta, and blues appearing yellow, and vice versa.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Lucy Hadden
 * @version 1.0
 */
public class NegativeFilter implements ImageOperation, java.io.Serializable {
    /**
     * The level of negative to apply.
     */
    public int negative;

    /**
     * <p>
     * Construct a negative filter with the given user-specified level.
     * </p>
     * 
     * @param negative The level of the newly constructed negative filter
     */
    public NegativeFilter(int negative) {
        this.negative = negative;
    }

    /**
     * <p>
     * Construct a negative filter with the default level.
     * </p>
     * 
     * <p>
     * By default, a negative filter has a level of 1.
     * </p>
     */
    public NegativeFilter() {
        this(1);
    }

    /**
     * <p>
     * This method applies the negative operation to a given BufferedImage object.
     * </p>
     * 
     * @param input the input BufferedImage object
     * 
     * @return the resulting BufferedImage object after the negative operation has
     *         been applied
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();

        // Create a new BufferedImage object with the same dimensions as the input image
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the pixel data from the input image
        int[] pixels = new int[width * height];
        input.getRGB(0, 0, width, height, pixels, 0, width);

        // Apply the negative operation to each pixel in the input image
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int r = negative - ((pixel >> 16) & 0xff); // Invert and adjust red component
            int g = negative - ((pixel >> 8) & 0xff); // Invert and adjust green component
            int b = negative - (pixel & 0xff); // Invert and adjust blue component

            // Clip the color values to ensure they are within the valid range
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            // Create the new pixel value with the inverted and adjusted color components
            int newPixel = (pixel & 0xff000000) | (r << 16) | (g << 8) | b;

            // Set the new pixel value in the result image
            result.setRGB(i % width, i / width, newPixel);
        }

        // Return the resulting BufferedImage object after the negative operation has
        // been applied
        return result;
    }
}
