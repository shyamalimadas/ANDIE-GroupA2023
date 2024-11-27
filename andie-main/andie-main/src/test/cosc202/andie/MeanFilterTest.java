package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.MeanFilter;

public class MeanFilterTest {

    @Test
    public void testDefaultConstructor() {
        MeanFilter mf = new MeanFilter();
        Assertions.assertEquals(1, mf.radius);

    }

    @Test
    public void notNullAssertions() {
        MeanFilter mf = new MeanFilter();
        Assertions.assertNotNull(mf);

    }
}
