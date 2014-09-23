package org.cjforge.hexed.states;

import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.play.hud.*;
import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class CanvasTests extends TestState {

    private Canvas canvas;

    public CanvasTests() {
        canvas = new Canvas(new Rectangle(50,50,300,400), new SimpleCanvasDrawer());
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        canvas.init(container, game);
        final Image image = Context.getInstance().resources().getImage("menu-bg");
        image.bind();
//        PaddedCanvasContent content = new PaddedCanvasContent(new CanvasContent() {
        CanvasContent content = new CanvasContent() {
            private int centerX = image.getWidth()/2;
            private int centerY = image.getHeight()/2;

            private ChangeListener listener;

            @Override
            public void move(int xPixels, int yPixels) {
                centerX += xPixels;
                centerY += yPixels;
                fire();
            }

            @Override
            public void performLeftButtonAction(Point contentCoordinates) {
                System.out.println("LMB clicked on " + contentCoordinates);
            }

            @Override
            public Point getCenteredPoint() {
                System.out.println("Image dimensions " + new Point(image.getWidth(), image.getHeight()));
                return new Point(centerX, centerY);
            }

            @Override
            public Image getFrameImage(Shape contentFrame) {
                return image.getSubImage((int) contentFrame.getMinX(), (int) contentFrame.getMinY(), (int) contentFrame.getWidth(), (int) contentFrame.getHeight());
            }

            @Override
            public int getWidth() {
                return image.getWidth();
            }

            @Override
            public int getHeight() {
                return image.getHeight();
            }

            @Override
            public void unregisterChangeListener(ChangeListener changeListener) {
                listener = listener == changeListener ? null : listener;
            }

            @Override
            public void registerChangeListener(ChangeListener changeListener) {
                listener = changeListener;
            }

            private void fire() {
                listener.applyChanges();
            }
        };
//        content.setLeftPadding(30);
//        content.setRightPadding(100);
//        content.setUpperPadding(30);
//        content.setLowerPadding(100);
        canvas.setContent(content);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setBackground(Color.white);
        canvas.render(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        canvas.update(container, game, delta);
    }

    public static void main(String... args) throws SlickException {
        GameState[] states = {new CanvasTests()};
        TestGame.startGame("Canvas tests", states);
    }
}
