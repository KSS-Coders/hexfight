package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.board.Tile;
import org.cjcoders.hexfight.utils.Point;

import java.awt.*;

/**
 * Created by mrakr_000 on 02.05.14.
 */
public class TileDrawer {

    private TilesSettings settings;

    public TileDrawer(TilesSettings settings) {
        this.settings = settings;
    }

    public void drawTile(Tile tile, Point atGrid, Graphics2D g){
        Point panelXY = gridToPanelXY(atGrid);
        Polygon poly = createHex(panelXY);
        g.setPaint(tile.getFillImg());
        g.fillPolygon(poly);
        g.setColor(tile.getBorderColor());
        g.drawPolygon(poly);
    }


    public Polygon createHex(Point panelXY){
        Polygon hexagon = new Polygon();
        hexagon.addPoint(panelXY.x, panelXY.y);
        hexagon.addPoint(panelXY.x + settings.getSideSize(), panelXY.y);
        hexagon.addPoint(panelXY.x + settings.getDx(), panelXY.y + settings.getDy());
        hexagon.addPoint(panelXY.x + settings.getSideSize(), panelXY.y + 2 * settings.getDy());
        hexagon.addPoint(panelXY.x, panelXY.y + 2 * settings.getDy());
        hexagon.addPoint(panelXY.x - settings.getxPad(), panelXY.y + settings.getDy());
        return hexagon;
    }

    private Point gridToPanelXY(Point gridXY){
        int x = settings.getxPad() + gridXY.x * settings.getDx();
        int y = gridXY.y * settings.getDy() * 2;
        if(gridXY.x % 2 == 1) y += settings.getDy();
        return new Point(x, y);
    }
}
