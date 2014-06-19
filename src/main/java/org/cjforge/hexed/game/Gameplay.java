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

    public Player getPlayer(int id){
        return players[id];
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getNextPlayer(){
        if(++currentplayerId == players.length){
            nextTurn();
        }
        return players[currentplayerId];
    }

    public void nextTurn(){
        currentplayerId = 0;
    }

    public TileCalculator getCalculator() {
        return calculator;
    }
}
