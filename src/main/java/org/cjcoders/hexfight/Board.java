package org.cjcoders.hexfight;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.utils.ArrayGrid;
import org.cjcoders.hexfight.utils.Grid;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class Board{
    private static Logger l = Logger.getLogger(Board.class.getName());

    public Grid<Tile> getGrid() {
        return grid;
    }

    private Grid<Tile> grid;

    public Board(int width, int height) {
        grid = new ArrayGrid<>(width, height);
    }

    public static Board getDefault(int width, int height, int players) {
        Board b = new Board(width, height);
        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                b.grid.set(new Tile(x, y, 6), x, y);
            }
        }
        for(int i = 0; i < players; ++i){
            int x;
            int y;
            Tile tile;
            do {
                x = (int) (Math.random() * (width - 1));
                y = (int) (Math.random() * (height - 1));
                tile = b.grid.get(x,y);
            }while(tile.getTileNo() >= 6);
            l.debug("Player " + i + " home is on planet [" + x + ", " + y + "] with tileNo: " + tile.getTileNo());
            tile.setOwner(new Player(i));
            tile.setForces(TileForces.OWNED_DEFAULT);
        }
        return b;
    }
}
