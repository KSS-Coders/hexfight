package org.cjforge.hexed.states.play.hud;

import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class EmptyCanvas implements CanvasContent {
    private static CanvasContent instance = new EmptyCanvas();
    private EmptyCanvas(){}

    public static CanvasContent get() {return instance;}

    @Override
    public void move(int xPixels, int yPixels) {

    }

    @Override
    public void performLeftButtonAction(Point contentCoordinates) {

    }

    @Override
    public Point getCenteredPoint() {
        return new Point(0,0);
    }

    @Override
    public Image getFrameImage(Shape contentFrame) {
        try {
            return new Image(0,0);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void unregisterChangeListener(ChangeListener changeListener) {

    }

    @Override
    public void registerChangeListener(ChangeListener changeListener) {

    }
}
