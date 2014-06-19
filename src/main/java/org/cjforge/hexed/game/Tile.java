package org.cjforge.hexed.game;

import java.util.Collection;
import java.util.HashSet;

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
    private boolean active;

    private Collection<TileListener> listeners;

    public Tile(int x, int y, int tileNo){
        this.x = x;
        this.y = y;
        this.tileNo = tileNo;
        forces = new TileForces();
        listeners = new HashSet<>();
    }
    public Tile(int tileNo){
        this(0,0,tileNo);
    }

    public void addListener(TileListener listener){
        listeners.add(listener);
    }
    public void notifyListeners(){
        for(TileListener listener : listeners){
            listener.update(this);
        }
    }

    public int getTileNo() {
        return tileNo;
    }

    public boolean isOwned(){ return owner != null; }
    public boolean isNeutral(){ return owner == null && !forces.isEmpty(); }
    public boolean isVoid(){ return owner == null && forces.isEmpty(); }

    public Player getOwner(){ return owner; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void switchActive(){
        active = !active;
        notifyListeners();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        notifyListeners();
    }

    public TileForces getForces() {
        return forces;
    }

    public void setForces(TileForces forces) {
        this.forces = forces;
        notifyListeners();
    }

    public boolean isActive() {
        return active;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPlanet(){ return getTileNo() >= 0; }
}
