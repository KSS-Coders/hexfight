package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.utils.MathUtils;
import org.cjcoders.hexfight.utils.Point;

/**
 * Created by mrakr_000 on 02.05.14.
 */
public class TilesSettings {

   /*============================================
     HELPER FIELDS
     These fields are used to store commonly
     used values instead of evaluating them
     every time
    ===========================================*/

    /** Tile's side size in pixels. */
    private int sideSize;
    /** Distance between extreme peaks of hexagon and the nearest peak on X axis */
    private int xPad;
    /** Distance between one of top/bottom peaks and the far peak on X axis. */
    private int dx;
    /** Distance between top and bottom peaks on Y axis */
    private int dy;
    /** Offset of the upper left corner of the board from off screen upper left corner. */
    private Point offset;

    public TilesSettings(int sideSize, Point offset) {
        this.offset = offset;
        setSideSize(sideSize);
    }
    public void setSideSize(int sideSize){
//
        this.sideSize = sideSize;
        xPad = (int)(sideSize * MathUtils.COS60);
        dx = sideSize * 3 / 2;
        dy = (int)(sideSize * MathUtils.SIN60);
    }

    public int getSideSize() {
        return sideSize;
    }

    public int getDy() {

        return dy;
    }

    public int getDx() {

        return dx;
    }

    public int getxPad() {

        return xPad;
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }
    public void updateOffset(int xOffset, int yOffset){
        setOffset(new Point(offset.x + xOffset, offset.y + yOffset));
    }
}
