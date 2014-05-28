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

    private Board board = Board.getDefault(15, 15, 3);

    public void tileClicked(Point p) {
        l.info("Tile " + p + " clicked");
    }
}
