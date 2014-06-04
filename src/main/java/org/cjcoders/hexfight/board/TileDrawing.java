package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.utils.Profiler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;

import java.util.Collection;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class TileDrawing implements TileListener {

    private TileDrawer drawer;

    public Tile getTile() {
        return tile;
    }

    private Tile tile;
    private Collection<TileDrawingLayer> layers;

    public TileDrawing(TileDrawer drawer, Tile tile) {
        this.drawer = drawer;
        this.tile = tile;
        update(tile);
        tile.addListener(this);
    }

    public Shape getShape() {
        return drawer.getShape(drawer.getX(tile.getX(), tile.getY()), drawer.getY(tile.getX(), tile.getY()));
    }

    public void render(GUIContext container, Graphics g, int xOffset, int yOffset) {
        for(TileDrawingLayer layer : layers) {
            layer.render(this, container, g, xOffset, yOffset);
        }
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
        Profiler p = new Profiler("TileDrawing", Profiler.MICROS);
        p.start();
        layers = drawer.getDrawing(tile);
        p.log("Get drawing");
        //for(TileDrawingLayer layer : layers) layer.init(this);
        p.log("Init drawing");
        p.logFromStart("Whole method");
    }
}
