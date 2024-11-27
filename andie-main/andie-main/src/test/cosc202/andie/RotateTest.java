package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.Rotate;

public class RotateTest {

    // check the default constructor
    @Test
    public void testDefaultConstructor() {
        Rotate r = new Rotate(90);
        Assertions.assertEquals(90, r.getRotationAngle());
    }

    // check if the object is not null
    @Test
    public void notNullAssertions() {
        Rotate r = new Rotate(90);
        Assertions.assertNotNull(r);

    }
}