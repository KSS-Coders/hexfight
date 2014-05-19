package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.Point;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class OffsetChangedEvent {
    public final Point oldOffset;
    public final Point newOffset;

    public OffsetChangedEvent(Point oldOffset, Point newOffset) {
        this.oldOffset = oldOffset;
        this.newOffset = newOffset;
    }
}
