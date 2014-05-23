package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.game.Player;

import java.util.Random;

/**
* Created by mrakr_000 on 2014-05-13.
*/
public class Tile{

    public static final int FILL_SPAWN_RATE = 4;

    private int x;
    private int y;

    private Player owner;
    private int tileNo;
    private TileForces forces;

    public Tile(int x, int y, int tileNo){
        this.x = x;
        this.y = y;
        this.tileNo = tileNo;
        forces = TileForces.NEUTRAL_DEFAULT();
    }

    public int getTileNo() {
        return tileNo;
    }

    public boolean isOwned(){ return owner != null; }
    public Player getOwner(){ return owner; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public TileForces getForces() {
        return forces;
    }

    public void setForces(TileForces forces) {
        this.forces = forces;
    }
}
