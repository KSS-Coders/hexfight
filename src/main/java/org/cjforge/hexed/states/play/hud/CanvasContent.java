package org.cjforge.hexed.states.play.hud;

import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public interface CanvasContent {
    void move(int xPixels, int yPixels);

    void performLeftButtonAction(Point contentCoordinates);

    Point getCenteredPoint();

    Image getFrameImage(Shape contentFrame);

    int getWidth();

    int getHeight();

    void unregisterChangeListener(ChangeListener changeListener);

    void registerChangeListener(ChangeListener changeListener);
}
