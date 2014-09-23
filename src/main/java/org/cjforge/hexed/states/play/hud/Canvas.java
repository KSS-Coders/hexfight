package org.cjforge.hexed.states.play.hud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.InputAdapter;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class Canvas{

    private Logger log = LogManager.getLogger(this.getClass());

    private CanvasDrawer drawer;

    public CanvasContent getContent() {
        return content;
    }

    public Shape getArea() {
        return area;
    }

    private CanvasContent content;
    private Shape area;
    private InputAdapter inputAdapter;
    private ChangeListener changeListener;

    private Image currentFrame;

    public Canvas(Shape area, CanvasDrawer drawer) {
        this.area = area;
        this.drawer = drawer;
        this.content = EmptyCanvas.get();
        this.changeListener = new ContentChangedListener();
        this.inputAdapter = new CanvasInputAdapter();
    }

    public void init(GUIContext context, Game game) {
        Input input = context.getInput();
        input.addMouseListener(inputAdapter);
        currentFrame = getEmptyImage(0,0);
    }

    public void update(GUIContext context, Game game, int delta) {

    }

    public void render(GUIContext context, Game game, Graphics g) {
        g.drawImage(currentFrame, area.getMinX(), area.getMinY());
    }

    public void setContent(CanvasContent content) {
        this.content.unregisterChangeListener(changeListener);
        this.content = content;
        checkCurrentFrame();
        content.registerChangeListener(changeListener);
    }

    private void checkCurrentFrame(){
        currentFrame = drawer.drawCanvasContent(content, area);
    }

    private class CanvasInputAdapter extends InputAdapter{
        @Override
        public void mouseDragged(int oldx, int oldy, int newx, int newy) {
            log.debug("old: " + new Point(oldx, oldy) + " new " + new Point(newx, newy));
            if(area.contains(oldx, oldy)) {
                log.debug("draging by " + new Point(oldx - newx, oldy - newy));
                moveContent(oldx - newx, oldy - newy);
            }
        }

        @Override
        public void mouseClicked(int button, int x, int y, int clickCount) {
            if(area.contains(x, y)) {
                Point contentCoordinates = CanvasCoordiates.canvasPointToContentPoint(area, new Point(x,y));
                switch(button) {
                    case Input.MOUSE_LEFT_BUTTON:
                        content.performLeftButtonAction(contentCoordinates);
                }
            }
        }
    }

    private class ContentChangedListener implements ChangeListener{
        @Override
        public void applyChanges() {
            checkCurrentFrame();
        }
    }

    protected void moveContent(int xOffset, int yOffset) {
        getContent().move(xOffset, yOffset);
        if(!contentFitsAreaX()) {
            getContent().move(-xOffset, 0);
        }
        if(!contentFitsAreaY()) {
            getContent().move(0, -yOffset);
        }
    }

    private boolean contentFitsAreaX() {
        Point contentCenter = getContent().getCenteredPoint();
        Point upperLeft = contentCenter.add(new Point((int) area.getWidth()/2, (int) area.getHeight()/2).negate());
        Point lowerRight = contentCenter.add(new Point((int) area.getWidth()/2, (int) area.getHeight()/2));
        return upperLeft.x >= 0 && lowerRight.x < getContent().getWidth();
    }

    private boolean contentFitsAreaY() {
        Point contentCenter = getContent().getCenteredPoint();
        Point upperLeft = contentCenter.add(new Point((int) area.getWidth()/2, (int) area.getHeight()/2).negate());
        Point lowerRight = contentCenter.add(new Point((int) area.getWidth() / 2, (int) area.getHeight() / 2));
        return upperLeft.y >= 0 && lowerRight.y < getContent().getHeight();
    }

    private static Image getEmptyImage(int width, int height){
        try {
            return new Image(width, height);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }
}
