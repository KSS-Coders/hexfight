package org.cjforge.hexed.game;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.utils.ArrayGrid;
import org.cjforge.hexed.utils.Grid;
import org.cjforge.hexed.utils.Point;
import org.cjforge.hexed.utils.Profiler;

import java.util.*;

/**
 * Created by mrakr_000 on 2014-06-05.
 */
public class BoardFactory {

    private Logger l = Logger.getLogger(this.getClass());
    private TileFactory tileFactory = new TileFactory();

    public GameBoard buildSimpleBoard(int rows, int cols, Player[] players){
        Profiler p = new Profiler(this.getClass().getName(), Profiler.MICROS);
        p.start();
        List<Tile> tiles = new ArrayList<>();
        int tileCount = rows * cols;
        double nonEmptyTilesFactor = Context.getInstance().config().getNonEmptyTilesFactor();
        int nonEmptyTilesCount = (int) (tileCount * nonEmptyTilesFactor);
        tiles.addAll(Arrays.asList(tileFactory.getPlayerHomeTiles(players)));
        tiles.addAll(Arrays.asList(tileFactory.getNonEmptyTiles(nonEmptyTilesCount)));
        tiles.addAll(Arrays.asList(tileFactory.getEmptyTiles(tileCount - nonEmptyTilesCount - players.length)));
        Grid<Tile> grid = formRectangleGrid(tiles.toArray(new Tile[tiles.size()]), rows, cols);
        p.logFromStart("Board creation duration");
        return new GameBoard(grid);
    }

    private Grid<Tile> formRectangleGrid(Tile[] tiles, int rows, int cols) {
        Grid<Tile> grid = new ArrayGrid<>(rows, cols);
        Point[] points = getPoints(rows, cols);
        shuffleArray(points);
        for(int i = 0; i < tiles.length; ++i){
            tiles[i].setX(points[i].y);
            tiles[i].setY(points[i].x);
            grid.set(tiles[i], points[i].x, points[i].y);
            if(tiles[i].isOwned())l.info("Player tile : " + new Point(tiles[i].getX(), tiles[i].getY()) + " ; tileNo : " + tiles[i].getTileNo());
        }
        return grid;
    }

    private Point[] getPoints(int rows, int cols) {
        Point[] result = new Point[rows * cols];
        for(int r = 0; r < rows; ++r){
            for(int c = 0; c < cols; ++c){
                result[r * cols + c] = (new Point(r,c));
            }
        }
        return result;
    }

    private <T> void  shuffleArray(T[] ar){
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            T a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
