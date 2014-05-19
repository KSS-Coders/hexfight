package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.MathUtils;
import org.cjcoders.hexfight.old.utils.Point;

import java.awt.*;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class HexShaper implements TileShaper {
    public int getSideSize() {
        return (int)(sideSize * scale);
    }

    private int sideSize;
    private double scale;

    public HexShaper(int sideSize) {
        this.sideSize = sideSize;
        scale = 1;
    }

    @Override
    public Point convPanelToGrid(Point panelXY) {
        int x = (panelXY.x - getxPad()) / getDx();
        int y = (panelXY.y) / (2 * getDy());
        if(x % 2 == 1) y = (panelXY.y - getDy()) / (2 * getDy());
        return new Point(x, y);
    }


    @Override
    public Point convGridToPanel(Point gridXY) {
        int x = getxPad() + gridXY.x * getDx();
        int y = gridXY.y * getDy() * 2;
        if(gridXY.x % 2 == 1) y += getDy();
        return new Point(x, y);
    }

    @Override
    public int calculateBoardWidth(int columns) {
        return columns * getDx() + getxPad()+1;

    }

    private int getxPad() {
        return (int)(getSideSize() * MathUtils.COS60);
    }

    private int getDx() {
        return getSideSize() * 3 / 2;
    }

    private int getDy() {
        return (int)(getSideSize() * MathUtils.SIN60);
    }

    @Override
    public int calculateBoardHeight(int rows) {
        return rows * 2 * getDy() + getDy()+1;
    }

    @Override
    public Shape formTile(Point panelXY) {
        Polygon hexagon = new Polygon();
        hexagon.addPoint(panelXY.x, panelXY.y);
        hexagon.addPoint(panelXY.x + getSideSize(), panelXY.y);
        hexagon.addPoint(panelXY.x + getDx(), panelXY.y + getDy());
        hexagon.addPoint(panelXY.x + getSideSize(), panelXY.y + 2 * getDy());
        hexagon.addPoint(panelXY.x, panelXY.y + 2 * getDy());
        hexagon.addPoint(panelXY.x - getxPad(), panelXY.y + getDy());
        return hexagon;
    }

    @Override
    public void setScale(double scale) {
        if(scale < 0.1) return;
        this.scale = scale;
    }

    @Override
    public double getScale() {
        return scale;
    }
}
