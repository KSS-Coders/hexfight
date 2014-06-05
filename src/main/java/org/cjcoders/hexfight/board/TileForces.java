package org.cjcoders.hexfight.board;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class TileForces {
    public TileForces(int strength) {
        this.strength = strength;
    }
    public TileForces(){ this(0); }

    private int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        if(this.strength < 0) this.strength = 0;
    }

    public boolean isEmpty(){
        return strength == 0;
    }
}
