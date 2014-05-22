package org.cjcoders.hexfight.utils;

/**
 * Created by Max on 2014-05-05.
 */
public interface TileCalculator {
    int distCalculate(Point firstPoint, Point secondPoint);
    boolean isNearby(Point firstPoint, Point secondPoint);

    int getScreenXFor(int tileX, int tileY, int tileWidth);

    int getScreenYFor(int tileX, int tileY, int tileHeight);
}
