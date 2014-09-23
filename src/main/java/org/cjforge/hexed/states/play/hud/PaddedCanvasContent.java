package org.cjforge.hexed.states.play.hud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cjforge.hexed.utils.ColorFill;
import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class PaddedCanvasContent implements CanvasContent{
    private Logger log = LogManager.getLogger(this.getClass());

    private final CanvasContent originalContent;

    private int leftPadding;
    private int rightPadding;
    private int upperPadding;
    private int lowerPadding;

    public PaddedCanvasContent(CanvasContent originalContent) {
        this.originalContent = originalContent;
    }

    @Override
    public void move(int xPixels, int yPixels) {
        originalContent.move(xPixels, yPixels);
    }

    @Override
    public void performLeftButtonAction(Point contentCoordinates) {
        Point leftUpperCoordinates = centerToLeftUpper(contentCoordinates);
        if(!isPointOnPadding(leftUpperCoordinates)) {
            originalContent.performLeftButtonAction(contentCoordinates);
        }
    }

    @Override
    public Point getCenteredPoint() {
        Point originalContentCenteredPoint = originalContent.getCenteredPoint();
        return originToSelf(originalContentCenteredPoint);
    }

    @Override
    public Image getFrameImage(Shape contentFrame) {
        if(shapeExceedsContent(contentFrame)) {
            Shape frameMatchedToContent = matchFrameToContent(contentFrame);
            log.debug("matched " + frameMatchedToContent.getWidth() + " " + frameMatchedToContent.getHeight());
            Shape translatedContentFrame = selfToOrigin(frameMatchedToContent);
            Image originalImage = originalContent.getFrameImage(translatedContentFrame);
            Image emptyImage = getEmptyImage((int) contentFrame.getWidth(), (int) contentFrame.getHeight());
            try {
                emptyImage.getGraphics().drawImage(originalImage, contentFrame.getMinX() - frameMatchedToContent.getMinX(), contentFrame.getMinY() - frameMatchedToContent.getMinY());
                log.debug("drawing on " + new Point((int) (contentFrame.getMinX() - frameMatchedToContent.getMinX()), (int) (contentFrame.getMinY() - frameMatchedToContent.getMinY())));
                emptyImage.getGraphics().fill(new Circle(50,50,10), new ColorFill(Color.red));
                return emptyImage;
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        } else {
            Shape translatedContentFrame = selfToOrigin(contentFrame);
            return originalContent.getFrameImage(translatedContentFrame);
        }
    }

    private Shape matchFrameToContent(Shape contentFrame) {
        float minX = contentFrame.getMinX() >= getLeftPadding()  ? contentFrame.getMinX() : getLeftPadding();
        float minY = contentFrame.getMinY() >= getUpperPadding() ? contentFrame.getMinY() : getUpperPadding();
        float maxX = contentFrame.getMaxX() <= getWidth() - getRightPadding() ? contentFrame.getMaxX() : getWidth() - getRightPadding();
        float maxY = contentFrame.getMaxY() <= getHeight() - getLowerPadding() ? contentFrame.getMaxY() : getHeight() - getLowerPadding();
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public int getWidth() {
        return originalContent.getWidth() + getLeftPadding() + getRightPadding();
    }

    @Override
    public int getHeight() {
        return originalContent.getHeight() + getUpperPadding() + getLowerPadding();
    }

    @Override
    public void unregisterChangeListener(ChangeListener changeListener) {
        originalContent.unregisterChangeListener(changeListener);
    }

    @Override
    public void registerChangeListener(ChangeListener changeListener) {
        originalContent.registerChangeListener(changeListener);
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public boolean isPointOnPadding(Point point) {
        return point.x < getLeftPadding()
                || point.x > (getWidth() - getRightPadding())
                || point.y < getUpperPadding()
                || point.y > (getHeight() - getLowerPadding());
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public int getUpperPadding() {
        return upperPadding;
    }

    public void setUpperPadding(int upperPadding) {
        this.upperPadding = upperPadding;
    }

    public int getLowerPadding() {
        return lowerPadding;
    }

    public void setLowerPadding(int lowerPadding) {
        this.lowerPadding = lowerPadding;
    }

    private boolean shapeExceedsContent(Shape shape) {
        return shape.getMinX() < getLeftPadding()
                || shape.getMaxX() > getWidth() - getRightPadding()
                || shape.getMinY() < getUpperPadding()
                || shape.getMaxY() > getHeight() - getLowerPadding();

    }

    private Point originToSelf(Point p) {
        return p.add(new Point(getLeftPadding(), getUpperPadding()));
    }

    private Point selfToOrigin(Point p) {
        return p.add(new Point(getLeftPadding(), getUpperPadding()).negate());
    }

    private Shape selfToOrigin(Shape s) {
        return s.transform(Transform.createTranslateTransform(-getLeftPadding(), -getUpperPadding()));
    }

    private Point centerToLeftUpper(Point p) {
        return p.add(getCenteredPoint());
    }

    private static Image getEmptyImage(int width, int height){
        try {
            return new Image(width, height);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }
}
