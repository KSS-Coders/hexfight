package org.cjcoders.hexfight.board;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class TileDrawing implements TileListener {

    private TileDrawer drawer;

    public Tile getTile() {
        return tile;
    }

    private Tile tile;
    private TileDrawingLayer firstLayer;

    public TileDrawing(TileDrawer drawer, Tile tile) {
        this.drawer = drawer;
        this.tile = tile;
        firstLayer = drawer.getDrawing(tile);
        firstLayer.init(this);
        tile.addListener(this);
    }

    public void render(GUIContext container, Graphics g, int xOffset, int yOffset) {
        firstLayer.render(this, container, g, xOffset, yOffset);
    }

    public int getX(){
        return drawer.getX(tile.getX(), tile.getY());
    }
    public int getY(){
        return drawer.getY(tile.getX(), tile.getY());
    }

    public int getSize() {
        return drawer.getTileSize();
    }

    public int getCenterX() {
        return drawer.getCenterX(tile.getX(), tile.getY());
    }

    public int getCenterY() {
        return drawer.getCenterY(tile.getX(), tile.getY());
    }

    @Override
    public void update(Tile tile) {
        firstLayer = drawer.getDrawing(tile);
        firstLayer.init(this);
    }
}
