package org.cjforge.hexed.states.play;

import org.apache.log4j.Logger;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.states.State;
import org.cjforge.hexed.states.play.board.Board;
import org.cjforge.hexed.states.play.hud.Hud;
import org.cjforge.hexed.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class NewPlayState extends BasicGameState {

    private Logger l = Logger.getLogger(this.getClass());

    private Board board;
    private Hud hud;

    private Gameplay gameplay;

    @Override
    public int getID() {
        return State.TURN.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        board = new Board(container, game);
//        hud = new Hud(container, game);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        board.render(container, game, g);
//        hud.render(container, game, g);
    }

    @Override
    public void update(final GameContainer container, final StateBasedGame game, int delta) throws SlickException {
        pollInput(container, game, delta);
        board.update(container, game, delta);


    }

    private void pollInput(GameContainer container, StateBasedGame game, int delta) {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_S)) {
            try {
                saveScreenshot(container);
            } catch (IOException e) {
                l.error("Could not save screen shot");
            } catch (SlickException e) {
                l.error("Could not make screen shot");
            }
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(State.PAUSE.getCode(), new EmptyTransition(), new VerticalSplitTransition());
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        board.mouseClicked(button, x, y, clickCount);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        board.mouseDragged(oldx, oldy, newx, newy);
    }

    private void saveScreenshot(GameContainer container) throws IOException, SlickException {
        Image screenShot = makeScreenShot(container);
        File file = new File("screenShot.png");
        file.createNewFile();
        new ImageIOWriter().saveImage(screenShot, "png", new FileOutputStream(file), false);
    }

    private Image makeScreenShot(GameContainer container) throws SlickException {
        Image screenShot = new Image(container.getWidth(), container.getHeight());
        container.getGraphics().copyArea(screenShot, 0, 0);
        return screenShot;
    }

    public void setup(Gameplay gameplay) {
        this.gameplay = gameplay;
        board.setup(gameplay);
//        hud.setup(gameplay);
    }
}