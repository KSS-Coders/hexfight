package org.cjforge.hexed.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class ColorFill implements ShapeFill {

    private final Color c;

    public ColorFill(Color c) {
        this.c = c;
    }

    @Override
    public Color colorAt(Shape shape, float x, float y) {
        return c;
    }

    @Override
    public Vector2f getOffsetAt(Shape shape, float x, float y) {
        return new Vector2f(1, 1);
    }
}
