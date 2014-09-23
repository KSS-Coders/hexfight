package org.cjforge.hexed.states.play.hud;

import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.geom.Shape;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class CanvasCoordiates {
    private CanvasCoordiates(){}

    public static Point canvasPointToContentPoint(Shape canvasArea, Point canvasPoint) {
        Point canvasCenter = new Point((int) canvasArea.getCenterX(), (int) canvasArea.getCenterY());
        return canvasPoint.add(canvasCenter.negate());
    }
}
