package cosc202.andie;

import java.awt.Color;
import java.awt.image.*;

/**
 * <p>
 * A custom implementation of image convolution.
 * </p>
 * 
 * <p>
 * Convolve is similar to the java ConvolveOp class except the edges of an
 * image are handled differently.
 * If a pixel to sample is outside of the image then the nearest pixel is used.
 * </p>
 * 
 * @author Shyamalima Shreya Das.
 */
public class Convolve {

    private static final int OFFSET = 127;
    private static Kernel kernel;
    private static Boolean applyOffset;
    private static BufferedImage input;

    /**
     * <p>
     * Constructs a new convolve object.
     * </p>
     * 
     * @param k     The kernel to be used in the convolution.
     * @param input The image to be used in the convolution.
     */
    public Convolve(Kernel k, BufferedImage input) {
        this.kernel = k;
        this.applyOffset = false;
        this.input = input;
    }

    /**
     * <p>
     * Constructs a new convolve object with offset enabled.
     * </p>
     * 
     * @param k           The kernel to be used in the convolution.
     * @param applyOffset if true, an offset is applied to the convolution so that
     *                    negative results are now shifted to zero.
     */
    public Convolve(Kernel k, Boolean applyOffset) {
        kernel = k;
        this.applyOffset = applyOffset;
    }

    /**
     * <p>
     * Splits a color into different channels.
     * </p>
     * 
     * <p>
     * An integer color value is split into its different color channels and then
     * each channel is placed into an array.
     * </p>
     * 
     * @param local The color value to be split.
     * @return An array of color channels.
     */
    private static int[] extractColors(int local) {
        int[] colors = { (local & 0xFF000000) >>> 24,
                (local & 0x00FF0000) >>> 16, (local & 0x0000FF00) >>> 8, (local & 0x000000FF) };
        return colors;
    }

    /**
     * <p>
     * Combines color channels into one value.
     * </p>
     * 
     * <p>
     * Each color is recombined back into one integer value. If the color is outside
     * the region 0-255
     * it is then clipped.
     * </p>
     * 
     * @param colors The array of color channels to be recombined.
     * @return The one int value describing all of the color channels.
     */
    private static int combineColors(int[] colors) {
        int finalColor = 0;
        for (int i = 0; i < colors.length; i++) {
            finalColor = finalColor << 8;
            // If there is an offset then add it to colors[i] here.
            if (applyOffset) {
                colors[i] += OFFSET; // Offset applied.
            }
            if (colors[i] > 255) {
                // Clipping to large values.
                colors[i] = 255;
            }
            if (colors[i] < 0) {
                // Clipping to small values.
                colors[i] = 0;
            }
            finalColor = finalColor | colors[i];
        }
        return finalColor;
    }

    /**
     * <p>
     * Converts a int array to float array.
     * </p>
     * 
     * @param in The array to be converted.
     * @return The converted array.
     */
    private static float[] intsToFloats(int[] in) {
        float[] out = new float[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    /**
     * <p>
     * Converts a float array to int array.
     * </p>
     * 
     * @param in The array to be converted.
     * @return The converted array.
     */
    private static int[] floatsToInts(float[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = (int) Math.round(in[i]);
        }
        return out;
    }

    /**
     * <p>
     * Applies a convolution filter to an image.
     * </p>
     * 
     * <p>
     * The input image is used as the source for the convolution and the result of
     * the convolution
     * is put in the output image. If pixels are sampled that are outside of the
     * bounds of the image then the nearest
     * pixels are used instead.
     * </p>
     * 
     * @param input       The source image of the convolution.
     * @param kernel2     The kernel to be used in the convolution.
     * @param applyOffset If true, an offset is applied to the convolution so that
     * @return The output image.
     */
    public static BufferedImage filter(BufferedImage input, Kernel kernel2, Boolean applyOffset) {
        Convolve.applyOffset = applyOffset;
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // applyOffset = false;
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                float[] oColors = { 0.0f, 0.0f, 0.0f, 0.0f };
                for (int kx = 0; kx < kernel2.getWidth(); kx++) {
                    for (int ky = 0; ky < kernel2.getHeight(); ky++) {
                        int sampleX = x - kernel2.getXOrigin() + kx;
                        int sampleY = y - kernel2.getYOrigin() + ky;
                        if (sampleX < 0) {
                            sampleX = 0;
                        }
                        if (sampleY < 0) {
                            sampleY = 0;
                        }
                        if (sampleX >= input.getWidth()) {
                            sampleX = input.getWidth() - 1;
                        }
                        if (sampleY >= input.getHeight()) {
                            sampleY = input.getHeight() - 1;
                        }
                        float colors[] = intsToFloats(extractColors(input.getRGB(sampleX, sampleY)));
                        // Add kernel products to origin.
                        float kData[] = kernel2.getKernelData(null);
                        for (int i = 0; i < 4; i++) {
                            // For each color (a, r, g ,b)
                            oColors[i] += colors[i] * kData[ky * kernel2.getWidth() + kx];
                        }

                    }
                }
                // Restore the modified origin colors to the original origin colors.

                output.setRGB(x, y, combineColors(floatsToInts(oColors)));
            }
        }
        return output;
    }

    /**
     * <p>
     * Applies a convolution filter to an image that does not alter colors. This is
     * more efficient than the other and more accurate as well.
     * </p>
     * 
     * <p>
     * The convolution is performed by iterating over each pixel in the image and
     * computing the weighted sum of pixel values using the kernel.
     * The inner loops iterate over the kernel and access the corresponding pixel
     * values in the input image.
     * The resulting weighted sums are then used to calculate the new pixel values
     * for the output image.
     * </p>
     * 
     * @param image  The buffered image source image of the convolution.
     * @param kernel The kernel to be used in the convolution.
     * @return The output image.
     */
    public static BufferedImage convolve(BufferedImage image, Kernel kernel) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int kernelWidth = kernel.getWidth();
        int kernelHeight = kernel.getHeight();
        int kernelRadiusX = kernelWidth / 2;
        int kernelRadiusY = kernelHeight / 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float redSum = 0.0f;
                float greenSum = 0.0f;
                float blueSum = 0.0f;

                for (int i = 0; i < kernelWidth; i++) {
                    for (int j = 0; j < kernelHeight; j++) {
                        int pixelX = x + i - kernelRadiusX;
                        int pixelY = y + j - kernelRadiusY;

                        if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
                            Color color = new Color(image.getRGB(pixelX, pixelY));
                            float kernelValue = kernel.getKernelData(null)[j * kernelWidth + i];

                            redSum += kernelValue * color.getRed();
                            greenSum += kernelValue * color.getGreen();
                            blueSum += kernelValue * color.getBlue();
                        }
                    }
                }

                int red = Math.round(redSum);
                int green = Math.round(greenSum);
                int blue = Math.round(blueSum);

                red = Math.min(Math.max(red, 0), 255);
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);

                int rgb = new Color(red, green, blue).getRGB();
                output.setRGB(x, y, rgb);
            }
        }

        return output;
    }

}