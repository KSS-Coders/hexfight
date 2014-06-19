package org.cjforge.hexed.states.play.board;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cjforge.hexed.game.GameBoard;
import org.cjforge.hexed.game.Tile;
import org.cjforge.hexed.utils.Point;
import org.cjforge.hexed.utils.TileCalculator;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardDrawer {

    private final TileCalculator tileCalculator;
    private final TileDrawer tileDrawer;
    private Logger l = Logger.getLogger(this.getClass());

    public BoardDrawer(TileCalculator tileCalculator, TileDrawer tileDrawer) {
        this.tileCalculator = tileCalculator;
        this.tileDrawer = tileDrawer;
        l.setLevel(Level.DEBUG);
    }

    public Collection<TileDrawing> getDrawing(GameBoard gameBoard) {
        Collection<TileDrawing> result = new HashSet<>();
        for (Tile tile : gameBoard.getGrid()) {

            result.add(new TileDrawing(tileDrawer, tile));
        }
        return result;
    }

    public Point getBoardCooridnates(int x, int y) {
        return tileCalculator.getBorardCoordinates(x, y);
    }

    public int getBoardHeight(int rows, int cols) {
        return tileCalculator.getScreenYFor(rows - 1, cols - 1) + tileCalculator.getTileSize();
    }

    public int getBoardWidth(int rows, int cols) {
        return tileCalculator.getScreenXFor(rows - 1, cols - 1) + tileCalculator.getTileSize();
    }

}
