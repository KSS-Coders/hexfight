package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.game.GUICallback;
import org.cjcoders.hexfight.game.GUIRequest;
import org.cjcoders.hexfight.utils.Point;
import org.cjcoders.hexfight.utils.components.DialogBox;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.InternalTextureLoader;

/**
 * Created by mrakr_000 on 2014-05-15.
 */
public class BoardPanel {

    private static final int PADDING = 50;

    private int xOffset;
    private int yOffset;

    private Logger l = Logger.getLogger(this.getClass().getName());
    private BoardDrawing boardDrawing;

    public BoardPanel(BoardDrawing boardDrawing){
        this.boardDrawing = boardDrawing;
    }

    public void init(GameContainer container){
        boardDrawing.setVisibleSize(container.getScreenWidth(), container.getScreenHeight());
        xOffset = boardDrawing.getCenteredOffsetX();
        yOffset = boardDrawing.getCenteredOffsetY();
    }

    public Point getOffset(){
        return new Point(xOffset, yOffset);
    }

    public void update(GUIContext container){
        boardDrawing.setVisibleSize(container.getScreenWidth(), container.getScreenHeight());
    }

    public void render(GUIContext container, Graphics g){
        boardDrawing.render(container, g, xOffset, yOffset);

    }

    private boolean shouldUpdateXOffset(int dx){
        int newX = xOffset + dx;
        if (newX > PADDING && dx > 0) return false;
        if (-xOffset > boardDrawing.getMaxLeftBorder() && dx < 0) return false;
        return true;
    }
    private boolean shouldUpdateYOffset(int dy){
        int newY = yOffset + dy;
        if (newY > PADDING && dy > 0) return false;
        if (-yOffset > boardDrawing.getMaxUpBorder() && dy < 0) return false;
        return true;
    }
    private boolean shouldChangeXOffset(int x){
        if(boardDrawing.widthFitsVisible()) return false;
        return true;
    }
    private boolean shouldChangeYOffset(int y){
        if(boardDrawing.heightFitsVisible()) return false;
        return true;
    }

    public void updateOffset(int dx, int dy){
        if(shouldUpdateXOffset(dx)) setXOffset(xOffset + dx);
        if(shouldUpdateYOffset(dy)) setYOffset(yOffset + dy);
    }
    public void setXOffset(int x){
        if(shouldChangeXOffset(x)){
            l.info("Changing offset. board width is : " + boardDrawing.getFullWidth() + " and it fits " + boardDrawing.getWidth());
            xOffset = x;
        }
    }

    public void setYOffset(int y){
        if(shouldChangeYOffset(y)) yOffset = y;
    }

    public Point getAbsoluteCooridnates(int mx, int my) {
        int x = mx - xOffset;
        int y = my - yOffset;
        return new Point(x,y);
    }

    public Point getBoardCooridnates(int mx, int my) {
        Point abs = getAbsoluteCooridnates(mx, my);
        return boardDrawing.getTileCooridnates(abs.x, abs.y);
    }
}
