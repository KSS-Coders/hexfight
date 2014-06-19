package org.cjforge.hexed.utils;

import org.newdawn.slick.geom.Polygon;

/**
* Created by mrakr_000 on 2014-05-12.
*/
public class Hexagon extends Polygon {
    public Hexagon(int side, int x, int y){
        addPoint(x + 0.25f * side,y);
        addPoint(x + 0.75f * side,y);
        addPoint(x + side,y + 0.5f * side);
        addPoint(x + 0.75f * side,y + side);
        addPoint(x + 0.25f * side,y + side);
        addPoint(x,y + 0.5f * side);
        setClosed(true);
    }
}
