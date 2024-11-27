package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.SharpenFilter;

public class SharpenFilterTest {

    /* Testing the default constructor, should return 1 */
    @Test
    public void testDefaultConstructor() {
        SharpenFilter sf = new SharpenFilter();
        Assertions.assertEquals(1, sf.sharpen);

    }

    /* Testing that the input that the SharpenFilter takes is not null */
    @Test
    public void notNullAssertions() {
        SharpenFilter sf = new SharpenFilter();
        Assertions.assertNotNull(sf);

    }

    /**
     * Test the constructor of SharpenFilter that takes a sharpen value. Takes in
     * the value 50 and is expected to have a value of 50.
     */

    @Test
    public void testConstructor() {
        SharpenFilter sf = new SharpenFilter(50);
        Assertions.assertEquals(50, sf.sharpen);
    }

}
