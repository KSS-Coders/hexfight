package org.cjforge.hexed.utils.components;

/**
 * Created by mrakr_000 on 2014-06-04.
 */
public abstract class PositionedGraphicComponent implements GraphicComponent {

    /*============================================
        POSITION
     ===========================================*/
    private int x;
    private int y;
    private int width;
    private int height;
    public PositionedGraphicComponent(Integer x, Integer y, Integer width, Integer height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(Integer height) {
        this.height = height;
    }

}
