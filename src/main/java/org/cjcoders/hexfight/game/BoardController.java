package org.cjcoders.hexfight.game;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.board.Board;
import org.cjcoders.hexfight.board.Tile;
import org.cjcoders.hexfight.utils.Point;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class BoardController {

    private Logger l = Logger.getLogger(this.getClass());

    private Board board;

    public BoardController(Board board) {
        this.board = board;
    }

    public void tileClicked(Point p, GUICallback callback) {
        l.info("Tile " + p + " clicked");
        board.getGrid().get(p.y, p.x).switchActive();
        int i = callback.askForInt("Get int", 0, 10);
        l.info("Obtained i : " + i);
    }
}
