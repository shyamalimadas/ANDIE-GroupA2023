package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.*;

/*
 * <p>
 * A filter that apply emboss to image.
 * </p>
 * 
 * <p>
 * The emboss filter applies a convolution operation to the image.
 * </p>
 * 
 * @author Shyamalima Shreya Das
 */
public class EmbossFilter1 implements ImageOperation, java.io.Serializable {
    public EmbossFilter1() {

    }

    /**
     * <p>
     * Applies the Emboss filter to an input BufferedImage.
     * </p>
     * 
     * <p>
     * A convolution is applied to the image using a special kernel. Every pixels
     * final colour value is influenced by the
     * neighbouring pixels colour values. The amount a which the neighbours effect
     * the color is specified in a kernel.
     * </p>
     * 
     * <p>
     * This emboss goes from top left to bottom right.
     * </p>
     * 
     * @param input The image to apply the filter to.
     * 
     * @return Returns the an image with the Emboss filter applied.
     */

    public BufferedImage apply(BufferedImage input) {
        float[] array = { +1.0f, 0, 0,
                0, 0, 0,
                0, 0, -1.0f };

        Kernel kernel = new Kernel(3, 3, array);
        System.out.println("Before convolve");

        BufferedImage output = Convolve.filter(input, kernel, true);

        System.out.println("After convolve");

        return output;
    }

}
