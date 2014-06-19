package org.cjforge.hexed.states.play.board;

import org.apache.log4j.Logger;
import org.cjforge.hexed.game.GameBoard;
import org.cjforge.hexed.utils.components.Content;
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
    private GameBoard gameBoard;

    private Collection<TileDrawing> tiles;

    public BoardDrawing(BoardDrawer drawer, GameBoard gameBoard) {
        this.drawer = drawer;
        this.gameBoard = gameBoard;
        tiles = drawer.getDrawing(gameBoard);
    }

    @Override
    public void render(GUIContext container, Graphics g, Rectangle visibleArea) {
        for (TileDrawing d : tiles) {
            if (inRange(visibleArea, d.getShape())) {
                d.render(container, g, (int) visibleArea.getX(), (int) visibleArea.getY());
            }
        }
    }

    private boolean inRange(Shape area, Shape element) {
        return area.contains(element) || area.intersects(element);
    }

    @Override
    public int getWidth() {
        return drawer.getBoardWidth(gameBoard.getGrid().cols(), gameBoard.getGrid().rows());
    }

    @Override
    public int getHeight() {
        return drawer.getBoardHeight(gameBoard.getGrid().cols(), gameBoard.getGrid().rows());
    }

    @Override
    public int getCenterX() {
        return getWidth() / 2;
    }

    @Override
    public int getCenterY() {
        return getHeight() / 2;
    }
}
