package org.cjforge.hexed.utils;

import org.newdawn.slick.geom.Shape;

/**
 * Created by Max on 2014-05-05.
 */
public interface TileCalculator {
    int distCalculate(Point firstPoint, Point secondPoint);
    boolean isNearby(Point firstPoint, Point secondPoint);
    int getScreenXFor(int tileX, int tileY);
    int getScreenYFor(int tileX, int tileY);
    Point getBorardCoordinates(int screenX, int screenY);
    Shape getShape(int x, int y);
    int getTileSize();
}
