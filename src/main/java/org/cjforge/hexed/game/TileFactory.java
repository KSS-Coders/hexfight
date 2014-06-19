package org.cjforge.hexed.game;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by mrakr_000 on 2014-06-05.
 */
public class TileFactory {

    private Logger l = Logger.getLogger(this.getClass());

    public Tile[] getPlayerHomeTiles(Player[] players) {
        Tile[] tiles = getNonEmptyTiles(players.length);
        int forces = Context.getInstance().config().getInitialPlayerForces();
        for (int i = 0; i < players.length; ++i) {
            tiles[i].setOwner(players[i]);
            tiles[i].setForces(new TileForces(forces));
        }
        return tiles;
    }

    public Tile[] getNonEmptyTiles(int count) {
        Collection<Tile> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            result.add(getNonEmptyTile());
        }
        return result.toArray(new Tile[result.size()]);
    }

    private Tile getNonEmptyTile() {
        Random rand = new Random();
        int tilesNumber = Context.getInstance().config().getTilesNumber();
        int tileNo = rand.nextInt(tilesNumber);

        Tile t = new Tile(tileNo);
        t.setForces(new TileForces(Context.getInstance().config().getInitialNeutralForces()));
        return t;
    }

    public Tile[] getEmptyTiles(int count) {
        Tile[] tiles = new Tile[count];
        for (int i = 0; i < count; ++i) {
            tiles[i] = getEmptyTile();
        }
        return tiles;
    }

    private Tile getEmptyTile() {
        return new Tile(-1);
    }
}
