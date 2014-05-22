package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.utils.HexCalculator;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrakr_000 on 2014-05-15.
 */
public class BoardDrawer extends MouseOverArea{

    private static final int TILE_SIZE = 100;
    private static final int PADDING = 50;

    private Board board = Board.getDefault(30, 20, 3);
    private TileDrawer tileDrawer;
    private List<TileDrawing> tiles;
    private boolean xLocked;
    private boolean yLocked;
    private int xOffset = PADDING;
    private int yOffset = PADDING;
    private int screenWidth;
    private int screenHeight;

    private Logger l = Logger.getLogger(this.getClass().getName());

    public BoardDrawer(GUIContext c, TileDrawer tileDrawer) throws SlickException {
        super(c, new Image(0, 0), 0, 0);
        this.tileDrawer = tileDrawer;
        tiles = new ArrayList<>();
        for(Tile tile : board.getGrid()) {
            tiles.add(new TileDrawing(tileDrawer, tile, TILE_SIZE, TILE_SIZE));
        }

        if(getBoardWidth() < c.getWidth()){
            int xOffset = (c.getWidth() - getBoardWidth())/2;
            setXOffset(xOffset);
        }
        if(getBoardHeight() < c.getHeight()){
            int yOffset = (c.getHeight() - getBoardHeight())/2;
            setYOffset(yOffset);
        }
    }

    public int getBoardHeight(){
        Tile t2 = board.getGrid().get(board.getGrid().cols()-1, board.getGrid().rows()-1);
        return new HexCalculator().getScreenYFor(t2.getX(), t2.getY(), TILE_SIZE) + TILE_SIZE;
    }
    public int getBoardWidth(){
        Tile t2 = board.getGrid().get(board.getGrid().cols()-1, board.getGrid().rows()-1);
        return new HexCalculator().getScreenXFor(t2.getX(), t2.getY(), TILE_SIZE) + TILE_SIZE;
    }

    private int getRandNum() {
        int min = 0;
        int max = 100;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        updateOffset(dx,dy);
    }

    public void update(GUIContext container, int delta){
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();
    }

    public void render(GUIContext container, Graphics g){
        for(TileDrawing d : tiles){
            d.render(container, g);
        }
    }
    public void updateOffset(int dx, int dy){
        if(!xLocked) {
            xOffset += dx;
            if (xOffset > PADDING && dx > 0) xOffset = PADDING;
            else if (-xOffset > getBoardWidth() - screenWidth && dx < 0) xOffset = -(getBoardWidth() - screenWidth + PADDING);
            for (TileDrawing d : tiles) {
                d.setXOffset(xOffset);
            }
        }
        if(!yLocked) {
            yOffset += dy;
            if (yOffset > PADDING && dy > 0) yOffset = PADDING;
            else if (-yOffset > getBoardHeight() - screenHeight && dy < 0) yOffset = -(getBoardHeight() - screenHeight + PADDING);
            for (TileDrawing d : tiles) {
                d.setYOffset(yOffset);
            }
        }
    }
    public void setXOffset(int x){
        setXLocked(true);
        xOffset = x;
        for(TileDrawing d : tiles){
            d.setXOffset(x);
        }
    }

    public void setXLocked(boolean xLocked) {
        this.xLocked = xLocked;
    }

    public void setYLocked(boolean yLocked) {
        this.yLocked = yLocked;
    }

    public void setYOffset(int y){
        setYLocked(true);
        yOffset = y;
        for(TileDrawing d : tiles){
            d.setYOffset(y);
        }
    }

}
