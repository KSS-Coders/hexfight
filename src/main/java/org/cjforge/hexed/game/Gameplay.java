package org.cjforge.hexed.game;

import org.cjforge.hexed.utils.TileCalculator;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class Gameplay {
    private final GameBoard gameBoard;
    private final BoardController boardController;
    private final Player[] players;
    private final TileCalculator calculator;

    private int currentplayerId;

    public Gameplay(GameBoard gameBoard, Player[] players, TileCalculator calculator) {
        this.gameBoard = gameBoard;
        this.calculator = calculator;
        this.boardController = new BoardController(this);
        this.players = players;
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(int id) {
        return players[id];
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getNextPlayer() {
        boolean hasPlanets = false;
        do {
            if (++currentplayerId == players.length) {
                nextTurn();
            }
            for(Tile t : gameBoard.getGrid()){
                if(t.isOwned() && t. getOwner() == players[currentplayerId]){
                    hasPlanets = true;
                    break;
                }
            }
        } while(!hasPlanets);
        boardController.setActiveTile(null);
        return players[currentplayerId];
    }

    public void nextTurn() {
        for(Tile t : gameBoard.getGrid()){
            if(t.isExhausted()) t.setExhausted(false);
            if(t.isOwned() && t.isPlanet()) t.setForces(new TileForces((int) (t.getForces().getStrength() * 1.2  + 5)));
        }
        currentplayerId = 0;
    }

    public TileCalculator getCalculator() {
        return calculator;
    }

    public Player getCurrentPlayer(){ return players[currentplayerId]; }
}
