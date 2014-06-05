package org.cjcoders.hexfight.utils.components;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.utils.Point;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-06-04.
 */
public class DraggableContainer extends PositionedGraphicComponent {

    private Logger l = Logger.getLogger(this.getClass());

    public DraggableContainer(Integer positionX, Integer positionY, Integer visibleWidth, Integer visibleHeight, Content drawing) {
        super(positionX, positionY, visibleWidth, visibleHeight);
        this.drawing = drawing;
        this.frame = new VisibleFrame();
        focusOn(drawing.getCenterX(), drawing.getCenterY());
    }
    public DraggableContainer(Integer positionX, Integer positionY, Integer visibleWidth, Integer visibleHeight) {
        super(positionX, positionY, visibleWidth, visibleHeight);
        this.drawing = null;
        this.frame = new VisibleFrame();
    }

    /*============================================
        BACKGROUND
     ===========================================*/
    private Content background;

    public void setBackground(Content background) {
        this.background = background;
    }

    private boolean hasBackground() {
        return background != null;
    }

    /*============================================
            DRAWING
         ===========================================*/
    private Content drawing;

    public void setDrawing(Content drawing) {
        this.drawing = drawing;
        l.info("Setting drawing");
        focusOn(drawing.getCenterX(), drawing.getCenterY());
    }

    public void drag(int dx, int dy){
        frame.move(-dx, -dy);
    }

    public void drag(Point offset){
        drag(offset.x, offset.y);
    }

    public Point getOffset(){
        return new Point(-frame.getX(), -frame.getY());
    }

    public Point getAbsoluteCooridnates(int x, int y) {
        return frame.getDrawingCoordinates(x, y);
    }

    public void focusOn(int x, int y){
        frame.moveCenterTo(x,y);
    }

    /*============================================
        PADDING
     ===========================================*/
    private int paddingX;
    private int paddingY;

    public Integer getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(Integer paddingX) {
        this.paddingX = paddingX;
    }

    public Integer getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(Integer paddingY) {
        this.paddingY = paddingY;
    }

    /*============================================
        VISIBLE
     ===========================================*/
    private VisibleFrame frame;

    private class VisibleFrame {
        private int x;
        private int y;

        private void move(int dx, int dy){
            moveTo(getX() + dx, getY() + dy);
        }
        private void moveCenterTo(int x, int y){
            moveTo(x - getWidth()/2, y - getHeight()/2);
        }

        public Integer getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setCenterX(int x) {
            setX(x - getWidth() / 2);
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setCenterY(int y) {
            setY(y - getHeight() / 2);
        }

        private Rectangle getShape() {
            Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
            return r;
        }

        private Point getDrawingCoordinates(int xAtVisible, int yAtVisible) {
            return new Point(xAtVisible + getX(), yAtVisible + getY());
        }

        public void moveHorizontally(int x) {
            if (drawingFitsHorizontally()) centerOnDrawingHorizontally();
            else {
                setX(x);
                if (getX() < getMinVisibleOffsetX()) setX(getMinVisibleOffsetX());
                else if (getX() > getMaxVisibleOffsetX()) setX(getMaxVisibleOffsetX());
            }
        }

        private boolean drawingFitsHorizontally() {
            return getWidth() > drawing.getWidth();
        }

        private void centerOnDrawingHorizontally() {
            setCenterX(drawing.getCenterX());
        }

        public void moveVertically(int y) {
            if (drawingFitsVertically()) centerOnDrawingVertically();
            else {
                setY(y);
                if (getY() < getMinVisibleOffsetY()) setY(getMinVisibleOffsetY());
                else if (getY() > getMaxVisibleOffsetY()) setY(getMaxVisibleOffsetY());
            }
        }

        private boolean drawingFitsVertically() {
            return getHeight() > drawing.getHeight();
        }

        private void centerOnDrawingVertically() {
            setCenterY(drawing.getCenterY());
        }

        public void moveTo(int x, int y) {
            moveHorizontally(x);
            moveVertically(y);
        }
    }

    /*============================================
        OFFSET
     ===========================================*/
    public Integer getMinVisibleOffsetX(){
        return -getPaddingX();
    }

    public Integer getMinVisibleOffsetY(){
        return -getPaddingY();
    }

    public Integer getMaxVisibleOffsetX(){
        return drawing.getWidth() + getPaddingX() - getWidth();
    }

    public Integer getMaxVisibleOffsetY(){
        return drawing.getHeight() + getPaddingY() - getHeight();
    }

    /*============================================
        PINNED CONTENT
     ===========================================*/
    private Content pinnedContent;
    private int pinX;
    private int pinY;
    private boolean isPinned;

    public int getPinY() {
        return pinY;
    }

    public void setPinY(int pinY) {
        this.pinY = pinY;
    }

    public int getPinX() {
        return pinX;
    }

    public void setPinX(int pinX) {
        this.pinX = pinX;
    }

    public boolean hasPinned(){
        return pinnedContent != null && isPinned;
    }

    public void pin(){
        isPinned = true;
    }

    public void unpin(){
        isPinned = false;
    }

    public void pin(Content content, int xCenter, int yCenter){
        Point p = frame.getDrawingCoordinates(xCenter, yCenter);
        pinX = p.x;
        pinY = p.y;
        pinnedContent = content;
        isPinned = true;
    }

    private Rectangle placeOnFrame(){
        int xOnFrame = getPinX() - frame.getX() - pinnedContent.getWidth()/2;
        int yOnFrame = getPinY() - frame.getY() - pinnedContent.getHeight()/2;
        return new Rectangle(xOnFrame, yOnFrame, getWidth() - xOnFrame, getHeight() - yOnFrame);
    }

    /*============================================
        GraphicComponent IMPLEMENTATIONS
     ===========================================*/
    @Override
    public void init(GUIContext context, Game game) {
    }

    @Override
    public void update(GUIContext context, Game game, int delta) {

    }

    @Override
    public void render(GUIContext context, Game game, Graphics g){
        if(hasBackground()) background.render(context, g, new Rectangle(0, 0, getWidth(), getHeight()));
        if(drawing != null) drawing.render(context, g, frame.getShape());
        if(hasPinned()) pinnedContent.render(context, g, placeOnFrame());
    }
}