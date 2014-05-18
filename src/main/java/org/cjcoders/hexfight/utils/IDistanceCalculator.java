package org.cjcoders.hexfight.utils;

/**
 * Created by Max on 2014-05-05.
 */
public interface IDistanceCalculator {
    int distCalculate(Point firstPoint, Point secondPoint);
    boolean isNearby(Point firstPoint, Point secondPoint);
}
