package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.Resize;

public class ResizeTest {

    /*
     * This test verifies that the default constructor sets the size value to 1.
     */
    @Test
    public void testDefaultConstructor() {
        Resize r = new Resize(1);
        Assertions.assertEquals(1, r.size);

    }

    /*
     * This test verifies that the Resize object is not null when instantiated.
     */
    @Test
    public void notNullAssertions() {
        Resize r = new Resize(1);
        Assertions.assertNotNull(r);

    }

    /*
     * This test verifies that the default constructor sets the size value to a
     * negative value.
     */
    @Test
    public void testNegativeSize() {
        Resize r = new Resize(-1);
        Assertions.assertEquals(-1, r.size);
    }

    /*
     * This test verifies that the default constructor sets the size value to a
     * positive value.
     */
    @Test
    public void testPositiveSize() {
        Resize r = new Resize(1);
        Assertions.assertEquals(1, r.size);
    }

    /*
     * This test verifies that the default constructor sets the size value to a zero
     * value.
     */
    @Test
    public void testZeroSize() {
        Resize r = new Resize(0);
        Assertions.assertEquals(0, r.size);
    }

}
