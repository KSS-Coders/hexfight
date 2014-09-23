package org.cjforge.hexed.slick;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import static org.junit.Assert.assertEquals;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class ShapeTests {

    @Test
    public void translateRectangleTest() {
        Shape rect = new Rectangle(10, 10, 100, 100);
        Shape newRect = rect.transform(Transform.createTranslateTransform(10,10));

        assertEquals(20, newRect.getMinX(), 0.1);
        assertEquals(20, newRect.getMinY(), 0.1);
    }
}
