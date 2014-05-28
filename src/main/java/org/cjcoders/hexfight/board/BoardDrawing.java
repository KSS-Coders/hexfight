package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.utils.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

import java.util.Collection;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardDrawing {
    private BoardDrawer drawer;
    private Board board;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int width;
    private Collection<TileDrawing> tiles;

    public BoardDrawing(BoardDrawer drawer, Board board, int height, int width) {
        this.drawer = drawer;
        this.board = board;
        this.height = height;
        this.width = width;
        tiles = drawer.getDrawing(board);
    }

    public void render(GUIContext container, Graphics g, int xOffset, int yOffset){
        for(TileDrawing d : tiles){
            if(inRange(d, xOffset, yOffset)) {
                d.render(container, g, xOffset, yOffset);
            }
        }
    }

    public int getFullWidth(){
        return drawer.getBoardWidth(board.getGrid().cols(), board.getGrid().rows());
    }
    public int getFullHeight(){
        return drawer.getBoardHeight(board.getGrid().cols(), board.getGrid().rows());
    }

    public void setVisibleSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getMaxLeftBorder(){
        return getFullWidth() - width;
    }
    public int getMaxUpBorder(){
        return getFullHeight() - height;
    }

    private boolean inRange(TileDrawing d, int xOffset, int yOffset) {
        return fitsHorizontaly(d, xOffset) && fitsVerticaly(d, yOffset);
    }

    public Point getTileCooridnates(int x, int y) {
        return drawer.getTileCooridnates(x, y);
    }

    private boolean fitsVerticaly(TileDrawing d, int yOffset) {
        return fitsUp(d, yOffset) && fitsDown(d, yOffset);
    }

    private boolean fitsDown(TileDrawing d, int yOffset) {
        return d.getY() < -yOffset + height;
    }

    private boolean fitsUp(TileDrawing d, int yOffset) {
        return d.getY() + d.getSize() > -yOffset;
    }

    private boolean fitsHorizontaly(TileDrawing d, int xOffset) {
        return fitsLeft(d, xOffset) && fitsRight(d, xOffset);
    }

    private boolean fitsRight(TileDrawing d, int xOffset) {
        return d.getX() < -xOffset + width;
    }

    private boolean fitsLeft(TileDrawing d, int xOffset) {
        return d.getX() + d.getSize() > -xOffset;
    }

    public boolean widthFitsVisible() {
        return getFullWidth() < width;
    }
    public boolean heightFitsVisible() {
        return getFullHeight() < height;
    }

    public int getCenteredOffsetX(){
        return (width - getFullWidth())/2;
    }
    public int getCenteredOffsetY(){
        return (height - getFullHeight())/2;
    }
}
