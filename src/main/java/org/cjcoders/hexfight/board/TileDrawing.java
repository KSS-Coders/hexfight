package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.utils.HexCalculator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class TileDrawing implements TileListener {

    private TileDrawer drawer;
    private Tile tile;
    private int width;
    private int height;
    private TileDrawingLayer firstLayer;

    public TileDrawing(TileDrawer drawer, Tile tile, int width, int height) {
        this.drawer = drawer;
        this.tile = tile;
        this.width = width;
        this.height = height;
        firstLayer = drawer.getDrawing(tile);
        firstLayer.init(this);
        tile.addListener(this);
    }

    public void render(GUIContext container, Graphics g, int xOffset, int yOffset) {
        firstLayer.render(this, container, g, xOffset, yOffset);
    }

    public int getX(){
        return new HexCalculator().getScreenXFor(tile.getX(), tile.getY(), width);
    }
    public int getY(){
        return new HexCalculator().getScreenYFor(tile.getX(), tile.getY(), height);
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

    @Override
    public void update(Tile tile) {
        firstLayer = drawer.getDrawing(tile);
        firstLayer.init(this);
    }
}
