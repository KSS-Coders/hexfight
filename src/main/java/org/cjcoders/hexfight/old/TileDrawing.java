package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.board.Tile;

import java.awt.*;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class TileDrawing {
    private final Shape shape;
    private final Tile tile;

    public TileDrawing(Shape shape, Tile tile) {
        this.shape = shape;
        this.tile = tile;
    }

    public Shape getShape() {
        return shape;
    }
    public Paint getFillPaint(){
        return tile.getFillImg();
    }
    public Color getBorderColor(){
        return tile.getBorderColor();
    }
}
