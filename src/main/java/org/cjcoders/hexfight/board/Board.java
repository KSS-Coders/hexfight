package org.cjcoders.hexfight.board;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.Player;
import org.cjcoders.hexfight.utils.ArrayGrid;
import org.cjcoders.hexfight.utils.Grid;
import org.cjcoders.hexfight.utils.Profiler;

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
        grid = new ArrayGrid<>(height, width);
    }
    public Board(Grid<Tile> grid){ this.grid = grid; }

    public static Board getDefault(int width, int height, int playersCount) {
        Profiler p = new Profiler(Board.class.getName(), Profiler.MICROS);
        p.start();
        Board b = new Board(width, height);
        Player[] players = new Player[playersCount];

        for(int i = 0; i < playersCount; ++i){
            players[i] = new Player(i);
            int x = (int) (Math.random() * (width - 1));
            int y = (int) (Math.random() * (height - 1));
            b.getGrid().set(new Tile(x, y, (int) (Math.random() * (5))), y, x);
            b.getGrid().get(y, x).setOwner(players[i]);
            b.getGrid().get(y, x).setForces(new TileForces(Context.getInstance().config().getInitialNeutralForces()));
            l.info("Player " + i + ": [" + x + ", " + y + "]");
        }

        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                if(b.grid.get(y, x) != null) continue;
                if(Math.random() > 0.25)
                    b.grid.set(new Tile(x, y, 6), y, x);
                else
                    b.grid.set(new Tile(x, y, (int) (Math.random() * (5))), y, x);
            }
        }
        p.logFromStart("Board creation duration");
        return b;
    }
}
