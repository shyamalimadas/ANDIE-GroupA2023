package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.GaussianFilter;

public class GaussianFilterTest {

    /*
     * testing the default constructor - I wrote this code and then saw if CoPilot
     * could suggest any other tests. It returned the same test.
     * Returns 1
     */
    @Test
    public void testDefaultConstructor() {
        GaussianFilter gf = new GaussianFilter();
        Assertions.assertEquals(1, gf.radius);

    }

    /*
     * testing to make sure that the filter cannot take in a null value
     */
    @Test
    public void notNullAssertions() {
        GaussianFilter gf = new GaussianFilter();
        Assertions.assertNotNull(gf);

    }

    /**
     * Tests the constructor of the the GaussianFilter that takes a radius value.
     * Takes in the value 50 and is expected to have a value of 50.
     */

    @Test
    public void testConstructor() {
        GaussianFilter gf = new GaussianFilter(50);
        Assertions.assertEquals(50, gf.radius);
    }
}
