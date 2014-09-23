package org.cjforge.hexed.states.play;

import org.apache.log4j.Logger;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.states.State;
import org.cjforge.hexed.states.play.board.Board;
import org.cjforge.hexed.states.play.hud.Hud;
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
 * The main state of the game. This state implements all of actual gameplay.
 */
public class PlayState extends BasicGameState {

    private Logger l = Logger.getLogger(this.getClass());

    private Board board;
    private Hud hud;

    @Override
    public int getID() {
        return State.TURN.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        board = new Board(container, game);
        hud = new Hud(container, game);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        board.render(container, game, g);
        hud.render(container, game, g);
    }

    @Override
    public void update(final GameContainer container, final StateBasedGame game, int delta) throws SlickException {
        if(hud.update(container, game, delta))
        board.update(container, game, delta);
        board.setHudWidth(hud.getHudWidth());
        pollInput(container, game, delta);
    }


    /**
     * Check container input. This method will check input and perform actions appropriate for whole of the state
     * (like entering Mein Menu on ESC.
     *
     * @param container Game container
     * @param game  Current game
     * @param delta Time delta
     */
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
        if(!hud.mouseClicked(button, x, y, clickCount))
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

    /**
     * This method allows game setup after creation and initialization of state. It is necessary for dynamic game
     * configuration
     *
     * @param gameplay Gameplay configuration.
     */
    public void setup(Gameplay gameplay) {
        board.setup(gameplay);
        hud.setup(gameplay);
    }
}