package org.cjcoders.hexfight.utils;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.board.Hexagon;
import org.cjcoders.hexfight.board.Tile;
import org.newdawn.slick.geom.Shape;

/**
 * Created by Max on 2014-05-05.
 */
public class HexCalculator implements TileCalculator {

    private Logger l = Logger.getRootLogger();
    private final int tileSize;

    public HexCalculator(int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public int distCalculate(Point firstPoint, Point secondPoint){
        int xDist = Math.abs(firstPoint.x - secondPoint.x);
        int yDist = Math.abs(firstPoint.y - secondPoint.y);
        int specialIngredient = intDivisionRoundUp(xDist,2);
        int result = xDist+yDist;

        if(specialIngredient <= yDist) result-=specialIngredient;
        else result-=yDist;

        return result;
    }
    @Override
    public boolean isNearby(Point firstPoint, Point secondPoint) {
        return distCalculate(firstPoint, secondPoint) == 1;
    }

    @Override
    public int getScreenXFor(int tileX, int tileY) {
        return (int) (tileX * 0.75 * tileSize);
    }

    @Override
    public int getScreenYFor(int tileX, int tileY) {
        return (int) ((tileY + 0.5 * (tileX % 2)) * tileSize);
    }

    // Returns exact result if quotient is integer or rounded up if not
    private int intDivisionRoundUp(int dividend, int divisor){
        return (dividend + divisor - 1)/divisor;
    }

    @Override
    public Point getBorardCoordinates(int screenX, int screenY){
        int i0 = (int) (screenX / (0.75 * tileSize) );
        int x0 = getScreenXFor(i0, 0);
        int j0 = (int) (screenY / (double) tileSize - 0.5 * (i0 % 2));
        l.info("x0 = " + x0 + " ; i0 = " + i0);
        if(screenY / (double) tileSize < 0.5) j0 -= ((i0+(j0+1)%2) % 2);
        if (screenX < x0 + 0.25 * tileSize) {
            int y0 = getScreenYFor(i0, j0);
            l.info("y0 = " + y0 + " ; j0 = " + j0);
            // Obaszar II lub III
            int x1 = screenX - x0;
            int y1 = screenY - y0;
            // Obszar III
            if (!((-x1 * 2 + tileSize / 2.0 - y1 < 0) && (x1 * 2 + tileSize / 2.0 - y1 > 0))) {
                // Nie ten hex
                l.info("Nie ten hex");
                --i0;
                j0 = (int) (screenY / (double) tileSize - 0.5 * (i0 % 2));
                if(screenY / (double) tileSize < 0.5) j0 -= ((i0+(j0+1)%2) % 2);
            }
        }
        return new Point(i0, j0);
    }

    @Override
    public Shape getShape(int x, int y) {
        return new Hexagon(getTileSize(),x,y);
    }

    @Override
    public int getTileSize() {
        return (int) tileSize;
    }
}
