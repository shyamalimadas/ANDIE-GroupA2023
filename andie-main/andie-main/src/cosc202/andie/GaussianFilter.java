package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian Blur (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Gaussian filter blurs an image by replacing each pixel by the average of
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
 * The resources used to create this class are:
 * https://imagej.nih.gov/ij/source/ij/plugin/filter/GaussianBlur.java
 * https://hackernoon.com/how-to-implement-gaussian-blur-zw28312m
 * https://imagej.nih.gov/ij/source/ij/plugin/filter/GaussianBlur.java
 * https://github.com/jliljebl/phantom2D/blob/master/com/jhlabs/image/GaussianFilter.java
 * https://www.tabnine.com/code/java/classes/ij.plugin.filter.GaussianBlur
 * https://stackoverflow.com/questions/39684820/java-implementation-of-gaussian-blur
 * 
 * The lab demonstrators also gave great help in understanding the code. They
 * pointed me in the right direction and helped me understand the code.
 * 
 * @see java.awt.image.ConvolveOp
 * @author Shyamalima Das
 * @version 1.0
 */
public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply.
     */
    public int radius;

    /**
     * <p>
     * Construct a gaussian filter with the given user-specified size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    public GaussianFilter(int radius) {
        this.radius = radius;

    }

    /**
     * <p>
     * Constructs a gaussian filter with the default size.
     * </p
     * >
     * <p>
     * By default, a gaussian filter has value of 1.
     * </p>
     * 
     * @see GaussianFilter(int)
     */
    public GaussianFilter() {
        this.radius = 1;
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {

        int sideLength = 2 * radius + 1; // the size of the sides of the kernel
        int size = sideLength * sideLength; // the size of the kernel
        float[] array = new float[size]; // the array of values for the kernel
        float variance = ((float) radius / 3); // the variance of the kernel, which is a third of the variance
        float variancesq = 2 * variance * variance; // the variance squared for the exponential part of the formula
        float sum = 0;
        int index = 0;
        for (int y = -radius; y <= radius; y++) { // starts from negative radius to start on the left side of the image
            for (int x = -radius; x <= radius; x++) {
                float value = (float) (Math.exp(-(x * x + y * y) / variancesq)); // the exponential part of the equation
                value /= (Math.PI * variancesq); // the rest of the equation
                array[index] = value; // the value is added to the array
                sum += value; // sum is the sum of all the values in the array
                index++; // index is indexed
            }
        }

        for (int z = 0; z < array.length; z++) {
            array[z] /= sum; // the array is divided by the sum to get the average
        }
        Kernel kernel = new Kernel(sideLength, sideLength, array);

        System.out.println("Before convolve");
        LoadingWindow loadingWindow = new LoadingWindow();
        loadingWindow.start();
        System.out.println("Before loading Window");
        BufferedImage output = Convolve.convolve(input, kernel);
        System.out.println("After loading Window");
        loadingWindow.stop();
        System.out.println("After convolve");

        return output;
    }

}
