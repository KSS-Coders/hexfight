package org.cjcoders.hexfight.board;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class TileForces {
    TileForces(int strength) {
        this.strength = strength;
    }

    private int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        if(this.strength < 0) this.strength = 0;
    }

    public static final TileForces NEUTRAL_DEFAULT(){ return new TileForces((int) (Math.random() * 4)); }
    public static final TileForces OWNED_DEFAULT = new TileForces(5);

    public boolean isCritical(){
        return strength < 5;
    }
    public boolean isEmpty(){
        return strength == 0;
    }
}
