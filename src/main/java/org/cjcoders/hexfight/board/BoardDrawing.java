package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.utils.components.Content;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;

import java.util.Collection;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardDrawing implements Content {

    private Logger l = Logger.getLogger(this.getClass());

    private BoardDrawer drawer;
    private Board board;

    private Collection<TileDrawing> tiles;

    public BoardDrawing(BoardDrawer drawer, Board board) {
        this.drawer = drawer;
        this.board = board;
        tiles = drawer.getDrawing(board);
    }

    @Override
    public void render(GUIContext container, Graphics g, Rectangle visibleArea){
        for(TileDrawing d : tiles){
            if(inRange(visibleArea, d.getShape())) {
                d.render(container, g, (int) visibleArea.getX(), (int) visibleArea.getY());
            }
        }
    }

    private boolean inRange(Shape area, Shape element){
        return area.contains(element) || area.intersects(element);
    }

    @Override
    public int getWidth(){
        return drawer.getBoardWidth(board.getGrid().cols(), board.getGrid().rows());
    }

    @Override
    public int getHeight(){
        return drawer.getBoardHeight(board.getGrid().cols(), board.getGrid().rows());
    }

    @Override
    public int getCenterX(){
        return getWidth()/2;
    }
    @Override
    public int getCenterY(){
        return getHeight()/2;
    }
}
