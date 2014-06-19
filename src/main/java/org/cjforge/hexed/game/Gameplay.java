package org.cjforge.hexed.game;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class Gameplay {
    private final GameBoard gameBoard;
    private final BoardController boardController;
    private final Player[] players;

    private int currentplayerId;

    public Gameplay(GameBoard gameBoard, BoardController boardController, Player[] players) {
        this.gameBoard = gameBoard;
        this.boardController = boardController;
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
}
