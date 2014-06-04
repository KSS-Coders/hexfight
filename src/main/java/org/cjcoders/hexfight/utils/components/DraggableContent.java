package org.cjcoders.hexfight.utils.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-06-04.
 */
public interface DraggableContent {
    void render(GUIContext container, Graphics g, Rectangle visibleArea);

    int getWidth();

    int getHeight();

    int getCenterX();

    int getCenterY();
}
