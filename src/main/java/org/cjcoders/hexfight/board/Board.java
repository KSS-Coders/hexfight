package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.game.Player;
import org.cjcoders.hexfight.utils.ArrayGrid;
import org.cjcoders.hexfight.utils.Grid;

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

    public static Board getDefault(int width, int height, int playersCount) {
        Board b = new Board(width, height);
        Player[] players = new Player[playersCount];

        for(int i = 0; i < playersCount; ++i){
            players[i] = new Player(i);
            int x = (int) (Math.random() * (width - 1));
            int y = (int) (Math.random() * (height - 1));
            b.getGrid().set(new Tile(x, y, (int) (Math.random() * (5))), x, y);
            b.getGrid().get(x, y).setOwner(players[i]);
            b.getGrid().get(x, y).setForces(TileForces.OWNED_DEFAULT);
        }

        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                if(b.grid.get(x, y) != null) continue;
                if(Math.random() > 0.25)
                    b.grid.set(new Tile(x, y, 6), x, y);
                else
                    b.grid.set(new Tile(x, y, (int) (Math.random() * (5))), x, y);
            }
        }
        for(int i = 0; i < playersCount; ++i){
            int x = (int) (Math.random() * (width - 1));
            int y = (int) (Math.random() * (height - 1));
        }
        return b;
    }
}
