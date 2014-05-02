package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrakr_000 on 02.05.14.
 */
public class TilesSettings {

    public static final int SIDE_SIZE_CHANGED = 1;
    public static final int OFFSET_CHANGED = 2;

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


    private List<ChangeListener> listeners;

    public int calculateBoardHeight(int rows){
        return rows * 2 * getDy() + getDy()+1;
    }
    public int calculateBoardWidth(int cols){
        return cols * getDx() + getxPad()+1;
    }

    public TilesSettings(int sideSize) {
        listeners = new ArrayList<>();
        setSideSize(sideSize);
    }
    public void addChangeListener(ChangeListener listener){
        listeners.add(listener);
    }
    public void removeChangeListener(ChangeListener listener){
        listeners.remove(listener);
    }
    public void setSideSize(int sideSize){
        this.sideSize = sideSize;
        xPad = (int)(sideSize * MathUtils.COS60);
        dx = sideSize * 3 / 2;
        dy = (int)(sideSize * MathUtils.SIN60);
        notifyListeners(SIDE_SIZE_CHANGED);
    }

    private void notifyListeners(int value){
        for(ChangeListener l : listeners) l.update(value);
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


}
