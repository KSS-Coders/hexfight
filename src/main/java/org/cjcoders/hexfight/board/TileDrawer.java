package org.cjcoders.hexfight.board;

import org.newdawn.slick.geom.Shape;

import java.util.Collection;

/**
 * Created by mrakr_000 on 2014-06-04.
 */
public interface TileDrawer {
    Shape getShape(int x, int y);

    Collection<TileDrawingLayer> getDrawing(Tile tile);

    int getX(int x, int y);

    int getY(int x, int y);

    int getTileSize();

    int getCenterX(int x, int y);

    int getCenterY(int x, int y);
}
