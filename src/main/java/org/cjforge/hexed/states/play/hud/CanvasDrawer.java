package org.cjforge.hexed.states.play.hud;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public interface CanvasDrawer {
    Image drawCanvasContent(CanvasContent content, Shape area);
}
