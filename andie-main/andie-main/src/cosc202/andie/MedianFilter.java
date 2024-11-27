package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by replacing each pixel with the
 * median of a square region of surrounding pixels. It takes all of the pixel
 * values in the local
 * neighbourhood and sorts them. The new pixel value is then the middle value
 * (the median) from the
 * sorted list.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Hayden Trow
 * @version 1.0
 */

public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of the filter to apply. A radius of 1 is a 3x3 filter, a radius of 2
     * a
     * 5x5 filter, and so forth.
     */
    public final int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */

    public MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     */

    public MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * The Median filter processes the input image by replacing each pixel with the
     * median
     * value of the pixels in its surrounding neighborhood, as specified by the
     * {@link radius}.
     * Larger Radii lead to a stronger effect.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */

    public BufferedImage apply(BufferedImage input) {
        int imgHeight = input.getHeight();
        int imgWidth = input.getWidth();
        int size = (2 * radius + 1) * (2 * radius + 1);
        int[] rMedian = new int[size];
        int[] gMedian = new int[size];
        int[] bMedian = new int[size];
        int kIndex = 0;
        Color nColor = new Color(0);
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {

                for (int kx = x - radius; kx <= x + radius; kx++) {
                    for (int ky = y - radius; ky <= y + radius; ky++) {

                        try {
                            nColor = new Color(input.getRGB(kx, ky), true);
                            rMedian[kIndex] = nColor.getRed();
                            gMedian[kIndex] = nColor.getGreen();
                            bMedian[kIndex] = nColor.getBlue();
                            kIndex++;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                }
                kIndex = 0;
                Arrays.sort(rMedian);
                Arrays.sort(gMedian);
                Arrays.sort(bMedian);

                Color fCol = new Color(rMedian[rMedian.length / 2], gMedian[gMedian.length /
                        2],
                        bMedian[bMedian.length / 2], nColor.getAlpha());
                input.setRGB(x, y, fCol.getRGB());

            }

        }
        return input;

    }

    /**
     * <p>
     * A simple method to find the median of a given array.
     * </p>
     * 
     * <p>
     * Because we must work with an integer array if there are an even number of
     * elements in the array, and the median result will be rounded down to nearest
     * whole number so may not be the exact median.
     * </p>
     * 
     * @param arr The array to find the median of.
     * @return The median of the array.
     */
    public static int findMedian(int[] arr) {
        Arrays.sort(arr);
        int size = arr.length;
        if (size % 2 == 0) {
            return (arr[size / 2 - 1] + arr[size / 2]) / 2;
        } else {
            return arr[size / 2];
        }
    }
}