package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.Tile;
import org.cjcoders.hexfight.utils.HexCalculator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class TileDrawing {
    private TileDrawer drawer;
    private Tile tile;
    private int width;
    private int height;
    private TileDrawingLayer firstLayer;
    private int xOffset = 50;
    private int yOffset = 50;

    public TileDrawing(TileDrawer drawer, Tile tile, int width, int height) {
        this.drawer = drawer;
        this.tile = tile;
        this.width = width;
        this.height = height;
        firstLayer = drawer.getDrawing(tile);
        firstLayer.init(this);
    }

    public void render(GUIContext container, Graphics g) {
        firstLayer.render(this, container, g);
    }

    public int getX(){
        return new HexCalculator().getScreenXFor(tile.getX(), tile.getY(), width) + xOffset;
    }
    public int getY(){
        return new HexCalculator().getScreenYFor(tile.getX(), tile.getY(), height) + yOffset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile() {
        return tile;
    }

    public int getCenterX() {
        return getX() + width / 2;
    }

    public int getCenterY() {
        return getY() + height / 2;
    }

    public void updateXOffset(int delta) {
        this.xOffset += delta;
    }

    public void updateYOffset(int delta) {
        this.yOffset += delta;
    }

    public void setXOffset(int XOffset) {
        this.xOffset = XOffset;
    }

    public void setYOffset(int YOffset) {
        this.yOffset = YOffset;
    }
}
