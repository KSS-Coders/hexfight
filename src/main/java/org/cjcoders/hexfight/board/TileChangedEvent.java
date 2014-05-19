package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.old.utils.Point;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class TileChangedEvent {
    public final Point tileXY;

    public TileChangedEvent(Point tileXY) {
        this.tileXY = tileXY;
    }
}
