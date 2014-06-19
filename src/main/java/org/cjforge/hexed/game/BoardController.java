package org.cjforge.hexed.game;

import org.apache.log4j.Logger;
import org.cjforge.hexed.states.play.GUICallback;
import org.cjforge.hexed.utils.Point;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardController {

    private Logger l = Logger.getLogger(this.getClass());

    private GameBoard gameBoard;

    public BoardController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void tileClicked(Point p, GUICallback callback) {
        l.info("Tile " + p + " clicked");
        gameBoard.getGrid().get(p.y, p.x).switchActive();
        int i = callback.askForInt("Get int", 0, 10);
        l.info("Obtained i : " + i);
    }
}
