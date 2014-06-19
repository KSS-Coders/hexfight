package org.cjforge.hexed.game;

import org.apache.log4j.Logger;
import org.cjforge.hexed.states.play.GUICallback;
import org.cjforge.hexed.utils.Point;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardController {

    private Logger l = Logger.getLogger(this.getClass());

    private Gameplay gameplay;

    private Tile activeTile;

    public BoardController(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void tileClicked(Point p, GUICallback callback) {
        l.info("Tile " + p + " clicked");
        Tile currentTile = gameplay.getGameBoard().getGrid().get(p.y, p.x);
        if (activeTile == null && currentTile.isOwned()) {
            setActiveTile(currentTile);
        } else {
            Point activeTileCoordinates = new Point(activeTile.getX(), activeTile.getY());
            if (gameplay.getCalculator().isNearby(p, activeTileCoordinates)) {
                int forcesCount = callback.askForForcesCount(0, activeTile.getForces().getStrength());
                l.info("Obtained i : " + forcesCount);
                if (forcesCount > 0) {
                    if (currentTile.getOwner() == activeTile.getOwner()) {
                        moveForces(activeTile, currentTile, forcesCount);
                    } else if (currentTile.isVoid()) {
                        conquerTile(activeTile, currentTile, forcesCount);
                    } else if (currentTile.isNeutral()) {
                        attackNeutralTile(activeTile, currentTile, forcesCount);
                    } else {
                        attackTile(activeTile, currentTile, forcesCount);
                    }
                }
                setActiveTile(null);
            } else {
                if (currentTile.isOwned()) {
                    setActiveTile(currentTile);
                } else {
                    setActiveTile(null);
                }
            }
        }
    }

    public void setActiveTile(Tile currentTile) {
        if (activeTile != null) activeTile.switchActive();
        activeTile = currentTile;
        if (currentTile != null) currentTile.switchActive();
    }

    private void attackTile(Tile from, Tile dest, int forcesCount) {

    }

    private void attackNeutralTile(Tile from, Tile dest, int forcesCount) {
        int diff = dest.getForces().getStrength() - forcesCount;
        dest.setForces(new TileForces(diff));
        if(diff < 0){
            dest.setForces(new TileForces(-diff));
            dest.setOwner(from.getOwner());
        }
        moveForcesFrom(from, forcesCount);
    }

    private void conquerTile(Tile from, Tile dest, int forcesCount) {
        dest.getForces().setStrength(forcesCount);
        dest.setOwner(from.getOwner());
        moveForcesFrom(from, forcesCount);
    }

    private void moveForces(Tile from, Tile dest, int forcesCount) {
        dest.getForces().setStrength(dest.getForces().getStrength() + forcesCount);
        dest.notifyListeners();
        moveForcesFrom(from, forcesCount);
    }

    private void moveForcesFrom(Tile from, int forcesCount) {
        from.getForces().setStrength(from.getForces().getStrength() - forcesCount);
        if (!from.isPlanet() && from.getForces().isEmpty()) from.setOwner(null);
    }

}


