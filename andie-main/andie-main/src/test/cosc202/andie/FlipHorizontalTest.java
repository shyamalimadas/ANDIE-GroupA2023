package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.FlipHorizontal;

public class FlipHorizontalTest {

    /*
     * This test verifies that the default constructer creates a fh object that is
     * not null.
     */
    @Test
    public void testDefaultConstructor() {
        FlipHorizontal fh = new FlipHorizontal();
        Assertions.assertNotNull(fh);

    }

    /*
     * This test verifies that the FlipHorizontal object is not null when
     * instantiated.
     */
    @Test
    public void notNullAssertions() {
        FlipHorizontal fh = new FlipHorizontal();
        Assertions.assertNotNull(fh);

    }

}
