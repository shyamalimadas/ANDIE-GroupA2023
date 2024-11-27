package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.FlipVertical;

public class FlipVerticalTest {

    /*
     * This test verifies that the default constructer creates a fh object that is
     * not null.
     */
    @Test
    public void testDefaultConstructor() {
        FlipVertical fv = new FlipVertical();
        Assertions.assertNotNull(fv);

    }

    /*
     * This test verifies that the FlipHorizontal object is not null when
     * instantiated.
     */
    @Test
    public void notNullAssertions() {
        FlipVertical fv = new FlipVertical();
        Assertions.assertNotNull(fv);

    }

}
