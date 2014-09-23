package org.cjforge.hexed.states.play.hud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class SimpleCanvasDrawer implements CanvasDrawer{

    private Logger log = LogManager.getLogger(this.getClass());

    @Override
    public Image drawCanvasContent(CanvasContent content, Shape area) {
        Point contentCenter = content.getCenteredPoint();
        log.debug("Content center " + contentCenter);
        Shape contentFrame = area.transform(Transform.createTranslateTransform(contentCenter.x - area.getCenterX(), contentCenter.y - area.getCenterY()));
        log.debug("translate: x - " + (contentCenter.x - area.getCenterX()) + " y - " + (contentCenter.y - area.getCenterY()));
        log.debug("Content frame [" + contentFrame.getMinX() + ", " + contentFrame.getMinY() + "]: " + contentFrame.getWidth() + "x" + contentFrame.getHeight());
        return content.getFrameImage(contentFrame);
    }
}
