package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public class OffScreenPosition{

    private Set<OffsetChangedListener> offsetChangedListeners;

    public OffScreenPosition() {
        this.offsetChangedListeners = new HashSet<>();
        offset = new Point(0, 0);
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point offset) {
        Point oldOffset = this.offset;
        this.offset = offset;
        fireOffsetChanged(new OffsetChangedEvent(oldOffset, offset));
    }

    private Point offset;

    public Point removeOffset(Point panelXY){
        return panelXY.add(offset.negative());
    }

    public void updateOffset(int xOffset, int yOffset) {

    }

    public void addOffsetChangedListener(OffsetChangedListener listener){
        offsetChangedListeners.add(listener);
    }

    private void fireOffsetChanged(OffsetChangedEvent e) {
        for(OffsetChangedListener listener : offsetChangedListeners){
            listener.performOffsetChanged(e);
        }
    }

}
