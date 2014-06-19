package org.cjforge.hexed.utils.components;

/**
 * Created by mrakr_000 on 2014-06-05.
 */
public abstract class SimpleContent implements Content {
    private int width;
    private int height;

    public SimpleContent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getCenterX() {
        return width / 2;
    }

    @Override
    public int getCenterY() {
        return height / 2;
    }
}
