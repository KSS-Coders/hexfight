package org.cjcoders.hexfight.board;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cjcoders.hexfight.utils.Point;
import org.cjcoders.hexfight.utils.TileCalculator;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardDrawer {

    private Logger l = Logger.getLogger(this.getClass());

    private final TileCalculator tileCalculator;
    private final MainTileDrawer tileDrawer;

    public BoardDrawer(TileCalculator tileCalculator, MainTileDrawer tileDrawer) {
        this.tileCalculator = tileCalculator;
        this.tileDrawer = tileDrawer;
        l.setLevel(Level.DEBUG);
    }

    public Collection<TileDrawing> getDrawing(Board board){
        Collection<TileDrawing> result = new HashSet<>();
        for(Tile tile : board.getGrid()) {

            result.add(new TileDrawing(tileDrawer, tile));
        }
        return result;
    }

    public Point getBoardCooridnates(int x, int y) {
        return tileCalculator.getBorardCoordinates(x, y);
    }

    public int getBoardHeight(int rows, int cols) {
        return tileCalculator.getScreenYFor(rows-1, cols-1) + tileCalculator.getTileSize();
    }

    public int getBoardWidth(int rows, int cols){
        return tileCalculator.getScreenXFor(rows-1, cols-1) + tileCalculator.getTileSize();
    }

}
