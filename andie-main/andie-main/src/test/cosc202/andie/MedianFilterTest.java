package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {

    @Test
    public void testDefaultConstructor() {
        MedianFilter mf = new MedianFilter();
        Assertions.assertEquals(1, mf.radius);

    }

    @Test
    public void notNullAssertions() {
        MedianFilter mf = new MedianFilter();
        Assertions.assertNotNull(mf);

    }

    /**
     * Test the constructor of MedianFilter that takes a radius value.
     */

    @Test
    public void testConstructor() {
        MedianFilter mf = new MedianFilter(50);
        Assertions.assertEquals(50, mf.radius);
    }

}
