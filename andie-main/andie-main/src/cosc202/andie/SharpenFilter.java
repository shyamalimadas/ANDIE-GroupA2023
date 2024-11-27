package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen (opposite of blur) filter.
 * </p>
 * 
 * <p>
 * A Sharpen filter unblurs an image by replacing each pixel by the average of
 * the
 * pixels in a surrounding neighbourhood, and can be implemented by a
 * convolution.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Shyamalima Das
 * @version 1.0
 */

public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply.
     */
    public int sharpen;

    /**
     * <p>
     * Construct a sharpen filter with the given user-specified size.
     * </p>
     * 
     * @param sharpen The level of the newly constructed SharpenFilter
     */
    public SharpenFilter(int sharpen) {
        this.sharpen = sharpen;
    }

    /**
     * <p>
     * Constructs a sharpen filter with the default size.
     * </p
     * >
     * <p>
     * By default, a sharpen filter has value of 1.
     * </p>
     * 
     * @see SharpenFilter(int)
     */
    public SharpenFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Sharpen filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Sharpen filter is implemented via convolution.
     * The size of the convolution kernel is specified by the radius.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (blurred)) image.
     */
    // At the moment the filter is for a fixed size of kernel, which is 3x3, which
    // means that we are not considering the edges at the moment
    // This issue will be addressed in the following project submission
    public BufferedImage apply(BufferedImage input) {
        // the formula for the mean filter has been replaced with the sharpen filter
        // equation

        float[] array = { 0, -1 / 2.0f, 0,
                -1 / 2.0f, 3, -1 / 2.0f,
                0, -1 / 2.0f, 0 };
        // fixed size 3x3 kernel
        Kernel kernel = new Kernel(3, 3, array);
        System.out.println("Before convolve");
        BufferedImage output = Convolve.convolve(input, kernel);
        System.out.println("After convolve");

        return output;
    }

}
