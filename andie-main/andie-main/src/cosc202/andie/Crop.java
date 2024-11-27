package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * <p>
 * Represents the operation of cropping an image.
 * </p>
 *
 * <p>
 * This class implements {@link ImageOperation} and defines the behavior of
 * cropping a BufferedImage.
 * </p>
 * 
 *
 * @author Hayden Trow
 */
public class Crop implements ImageOperation {

    /**
     * The selected area to be cropped from the image.
     */
    private Rectangle selection;

    /**
     * Constructs a new CropOperation with the specified selection.
     *
     * @param selection The area to be cropped from the image.
     */
    public Crop(Rectangle selection) {
        this.selection = new Rectangle(selection);
    }

    /**
     * Applies the crop operation to the specified image.
     * It returns a new image that is a cropped version of the input image.
     *
     * @param input The image to be cropped.
     * @return The cropped image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int type = input.getType();
        BufferedImage croppedImage = new BufferedImage(selection.width, selection.height, type);

        for (int x = 0; x < selection.width; x++) {
            for (int y = 0; y < selection.height; y++) {
                int rgb = input.getRGB(selection.x + x, selection.y + y);
                croppedImage.setRGB(x, y, rgb);
            }
        }

        return croppedImage;
    }
}