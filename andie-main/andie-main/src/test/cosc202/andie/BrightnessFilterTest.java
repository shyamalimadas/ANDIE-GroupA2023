package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.BrightnessFilter;

/**
 * 
 * This class contains JUnit tests for the BrightnessFilter class.
 * 
 * @author Lucy Hadden
 * @version 1.0
 */

public class BrightnessFilterTest {

    /**
     * This test verifies that the default constructor sets the brightness value to
     * 1.
     */
    @Test
    public void testDefaultConstructor() {
        BrightnessFilter bf = new BrightnessFilter();
        Assertions.assertEquals(1, bf.brightness);
    }

    /**
     * This test verifies that the BrightnessFilter object is not null when
     * instantiated.
     */
    @Test
    public void notNullAssertions() {
        BrightnessFilter bf = new BrightnessFilter();
        Assertions.assertNotNull(bf);
    }

    /**
     * Test the constructor of BrightnessFilter that takes a brightness value.
     */

    @Test
    public void testConstructor() {
        BrightnessFilter bf = new BrightnessFilter(50);
        Assertions.assertEquals(50, bf.brightness);
    }
}