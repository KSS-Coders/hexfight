package org.cjcoders.hexfight.utils;

/**
 * Created by Max on 2014-05-05.
 */
public class HexCalculator implements TileCalculator {
    public int distCalculate(Point firstPoint, Point secondPoint){
        int xDist = Math.abs(firstPoint.x - secondPoint.x);
        int yDist = Math.abs(firstPoint.y - secondPoint.y);
        int specialIngredient = intDivisionRoundUp(xDist,2);
        int result = xDist+yDist;

        if(specialIngredient <= yDist) result-=specialIngredient;
        else result-=yDist;

        return result;
    }
    public boolean isNearby(Point firstPoint, Point secondPoint) {
        return distCalculate(firstPoint, secondPoint) == 1;

    }

    @Override
    public int getScreenXFor(int tileX, int tileY, int tileWidth) {
        return (int) (tileX * 0.75 * tileWidth);
    }

    @Override
    public int getScreenYFor(int tileX, int tileY, int tileHeight) {
        return (int) ((tileY + 0.5 * (tileX % 2)) * tileHeight);
    }

    // Returns exact result if quotient is integer or rounded up if not
    private int intDivisionRoundUp(int dividend, int divisor){
        return (dividend + divisor - 1)/divisor;
    }

    @Override
    public Point getBorardCoordinates(int screenX, int screenY, int tileWidth, int tileHeight){
        int i0 = (int) (screenX / (0.75 * tileWidth) );
        int x0 = getScreenXFor(i0, 0, tileWidth);
        int j0 = (int) (screenY / (double) tileHeight - 0.5 * (i0 % 2));
        if(screenY / tileWidth < 0.5) j0 -= (i0 % 2);
        if (screenX < x0 + 0.25 * tileHeight) {
            int y0 = getScreenYFor(i0, j0, tileHeight);
            // Obaszar II lub III
            int x1 = screenX - x0;
            int y1 = screenY - y0;
            // Obszar III
            if (!((-x1 * 2 + tileHeight / 2.0 - y1 < 0) && (x1 * 2 + tileHeight / 2.0 - y1 > 0))) {
                // Nie ten hex
                --i0;
                j0 = (int) (screenY / tileHeight - 0.5 * (i0 % 2));
            }
        }
        return new Point(i0, j0);
    }
}
