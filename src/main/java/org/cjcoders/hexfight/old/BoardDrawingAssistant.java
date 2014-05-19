package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.board.Board;
import org.cjcoders.hexfight.board.Tile;
import org.cjcoders.hexfight.board.TileChangedEvent;
import org.cjcoders.hexfight.board.TileChangedListener;
import org.cjcoders.hexfight.old.utils.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class BoardDrawingAssistant implements TileChangedListener {

    private double scale = 1;

    public BoardDrawingAssistant(TileShaper shaper, Board board) {
        tileChangedListeners = new HashSet<>();
        sideSizeChangedListeners = new HashSet<>();
        this.shaper = shaper;
        this.board = board;
        board.addTileChangeListener(this);
    }

    /*============================================
        CONVERSIONS
     ===========================================*/
    private TileShaper shaper;
    public Point convPanelToGrid(Point panelXY) {
        return shaper.convPanelToGrid(panelXY);
    }
    public Point convGridToPanel(Point gridXY) {
        return shaper.convGridToPanel(gridXY);
    }

    /*============================================
        TURN INTERACTIONS
     ===========================================*/
    private Board board;
    public void tileClicked(Point panelXY) {
        board.tileClicked(convPanelToGrid(panelXY));
    }

    /*============================================
        TILES
     ===========================================*/
    private Set<TileChangedListener> tileChangedListeners;
    public void addTileChangedListener(TileChangedListener listener){
        tileChangedListeners.add(listener);
    }
    @Override
    public void performTileChanged(TileChangedEvent e) {
        fireTileChanged(e);
    }

    private void fireTileChanged(TileChangedEvent e) {
        for(TileChangedListener listener : tileChangedListeners){
            listener.performTileChanged(e);
        }
    }
    public TileDrawing getTile(Point gridXY){
        Tile tile = board.getTile(gridXY.x, gridXY.y);
        Shape shape = shaper.formTile(shaper.convGridToPanel(gridXY));
        return new TileDrawing(shape, tile);
    }
    public TileDrawing[] getAllTiles(){
        List<TileDrawing> drawings = new ArrayList<>();
        for(int w = 0; w < board.getSize().width; ++w){
            for( int h = 0; h < board.getSize().height; ++h) drawings.add(new TileDrawing(shaper.formTile(convGridToPanel(new Point(w, h))), board.getTile(w, h)));

        }
        return drawings.toArray(new TileDrawing[1]);
    }

    /*============================================
        SIDE SIZE
     ===========================================*/
    private Set<SideChangedListener> sideSizeChangedListeners;
    public double getScale() {
        return this.scale;
        //return shaper.getScale();
    }
    public void setScale(double scale) {
        if(scale > 1 || scale < 0.25) return;
        //shaper.setScale(scale);
        this.scale = scale;
        fireSideChanged();
    }
    public void addSideChangedListener(SideChangedListener listener){
        sideSizeChangedListeners.add(listener);
    }
    private void fireSideChanged() {
        for(SideChangedListener listener : sideSizeChangedListeners){
            listener.performSideChanged();
        }
    }

    /*============================================
        TURN DIMENSIONS
     ===========================================*/
    public int getBoardWidth(){
        return shaper.calculateBoardWidth(board.getWidth());
    }
    public int getBoardHeight(){
        return shaper.calculateBoardHeight(board.getHeight());
    }
}
