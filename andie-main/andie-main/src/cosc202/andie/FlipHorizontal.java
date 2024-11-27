package cosc202.andie;

import java.awt.image.*;
import java.awt.geom.AffineTransform;

/**
 * <p>
 * ImageOperation to flip an image on its horizontal axis.
 * </p>
 * 
 * <p>
 * The image produced by this operation is flipped about its horizontal axis.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Josh Lawson
 * @version 1.1
 */
public class FlipHorizontal implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new FlipHorizontal operation.
     * </p>
     */
    public FlipHorizontal() {

    }

    /**
     * <p>
     * Apply flip horizontal transformation to an image.
     * </p>
     * 
     * @param input The image to be flipped
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input) {
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-input.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        input = op.filter(input, null);

        return input;
    }

}