package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ContrastFilter;

/**
 * 
 * This class contains JUnit tests for the ContrastFilter class.
 * 
 * @author Lucy Hadden
 * @version 1.0
 */
public class ContrastFilterTest<BufferedImage> {

    /**
     * Test the default constructor of ContrastFilter.
     */

    @Test
    public void testDefaultConstructor() {
        ContrastFilter cf = new ContrastFilter();
        Assertions.assertEquals(1, cf.contrast);

    }

    /**
     * Test that a ContrastFilter object is not null when created with the default
     * constructor.
     */
    @Test
    public void notNullAssertions() {
        ContrastFilter cf = new ContrastFilter();
        Assertions.assertNotNull(cf);

    }

    /**
     * Test the constructor of ContrastFilter that takes a contrast value.
     */

    @Test
    public void testConstructor() {
        ContrastFilter filter = new ContrastFilter(50);
        Assertions.assertEquals(50, filter.contrast);
    }
}