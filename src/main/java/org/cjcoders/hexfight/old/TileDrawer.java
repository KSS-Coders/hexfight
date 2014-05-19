package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.BoardDrawingAssistant;
import org.cjcoders.hexfight.old.TileDrawing;

import java.awt.*;

/**
 * Created by mrakr_000 on 02.05.14.
 */
public class TileDrawer {

    private BoardDrawingAssistant assistant;

    public TileDrawer(BoardDrawingAssistant assistant) {
        this.assistant = assistant;
    }

    public void drawTile(TileDrawing tile, Graphics2D g){
        g.setPaint(tile.getFillPaint());
        g.fill(tile.getShape());
        g.setColor(tile.getBorderColor());
        g.draw(tile.getShape());
    }
}
