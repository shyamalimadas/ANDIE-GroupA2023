package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to adjust the brightness of an image
 * </p>
 * 
 * <p>
 * The images produced by this operation are still technically colour images,
 * in that they have red, green, and blue values, but each pixel has been
 * changed to
 * cater to the user-specified level of brightness
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
public class BrightnessFilter implements ImageOperation, java.io.Serializable {
    /**
     * The size of filter to apply.
     */
    public int brightness;

    /**
     * <p>
     * Construct a brightness filter with the given user-specified size.
     * </p>
     * 
     * @param brightness The level of the newly constructed brightnessFilter
     */
    public BrightnessFilter(int brightness) {
        this.brightness = brightness;
    }

    /**
     * <p>
     * Construct a brightness filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a brightness filter has value of 1.
     * </p>
     * 
     */
    public BrightnessFilter() {
        this(1);
    }

    /**
     * <p>
     * This method applies brightness to a given BufferedImage object.
     * </p>
     * 
     * @param input the input BufferedImage object
     * 
     * @return the resulting BufferedImage object after brightness has been applied
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();

        // Create a new BufferedImage object with the same dimensions as the input image
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the pixel data from the input image
        int[] pixels = new int[width * height];
        input.getRGB(0, 0, width, height, pixels, 0, width);

        // Calculate the offset value for brightness
        int offset = brightness < 0 ? -brightness : 0;

        // Create a lookup table to map the input pixel values to the new brightness
        // values
        int[] lookupTable = new int[256];
        for (int i = 0; i < 256; i++) {
            int j = i + brightness - offset;
            lookupTable[i] = j < 0 ? 0 : (j > 255 ? 255 : j);
        }

        // Apply the brightness to each pixel in the input image
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int r = (pixel >> 16) & 0xff;
            int g = (pixel >> 8) & 0xff;
            int b = pixel & 0xff;

            // Map the input pixel values to the new brightness values using the lookup
            // table
            int nr = lookupTable[r];
            int ng = lookupTable[g];
            int nb = lookupTable[b];

            // Create the new pixel value with the adjusted brightness values
            int newPixel = (pixel & 0xff000000) | (nr << 16) | (ng << 8) | nb;

            // Set the new pixel value in the result image
            result.setRGB(i % width, i / width, newPixel);
        }

        // Return the resulting BufferedImage object after brightness has been applied
        return result;
    }
}
