package cosc202.andie;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 
 * Chatgpt helped assit me with the following documentation!
 * 
 * A class that implements the ImageOperation interface to adjust the contrast
 * of a given BufferedImage.
 * 
 * The class takes an integer value for the contrast adjustment and applies it
 * to each pixel of the image.
 * 
 * The class implements the Serializable interface to allow instances to be
 * serialized and deserialized.
 * 
 * @author Lucy Hadden
 * @version 1.0
 */
public class ContrastFilter implements ImageOperation, java.io.Serializable {

    /**
     * The contrast adjustment value
     */
    public int contrast;

    /**
     * 
     * Constructor that takes an integer value for the contrast adjustment.
     * 
     * @param contrast The contrast adjustment value.
     */
    public ContrastFilter(int contrast) {
        this.contrast = contrast;
    }

    /**
     * 
     * Default constructor that sets the contrast adjustment to 1.
     */
    public ContrastFilter() {
        this(1);
    }

    /**
     * 
     * Apply the contrast adjustment to the given BufferedImage.
     * 
     * @param image The BufferedImage to adjust the contrast of.
     * 
     * @return A new BufferedImage with the contrast adjustment applied.
     */
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Create a new BufferedImage with the same dimensions as the input image
        BufferedImage adjustedImage = new BufferedImage(width, height, image.getType());

        // Calculate the contrast factor
        double factor = (259 * (contrast + 255)) / (255 * (259 - contrast));

        // Loop through each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the color of the current pixel
                Color color = new Color(image.getRGB(x, y));

                // Calculate the new color values based on the contrast factor
                int red = (int) (factor * (color.getRed() - 128) + 128);
                int green = (int) (factor * (color.getGreen() - 128) + 128);
                int blue = (int) (factor * (color.getBlue() - 128) + 128);

                // Ensure that the color values are within the valid range of 0-255
                red = Math.min(Math.max(red, 0), 255);
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);

                // Set the new color of the pixel in the adjusted image
                Color adjustedColor = new Color(red, green, blue);
                adjustedImage.setRGB(x, y, adjustedColor.getRGB());
            }
        }

        return adjustedImage;
    }
}