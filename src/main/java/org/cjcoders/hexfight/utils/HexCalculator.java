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
}
