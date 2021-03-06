package org.cjforge.hexed;

import org.apache.log4j.BasicConfigurator;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.intro.GameIntroState;
import org.cjforge.hexed.states.intro.KickState;
import org.cjforge.hexed.states.intro.PrepareGameState;
import org.cjforge.hexed.states.menu.MainMenuState;
import org.cjforge.hexed.states.play.PlayState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * The main class of the application. Here AppGameContainer is created, and all states added to game. Also all necessary
 * configuration is performed.
 */
public class GameCore extends StateBasedGame {

    public static final String GAME_TITLE = "Hexed";

    private Context context;

    public GameCore(String title) {
        super(title);
        context = Context.getInstance();
    }

    public static void main(String args[]) throws SlickException {
        BasicConfigurator.configure();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        AppGameContainer app = new AppGameContainer(new GameCore(GAME_TITLE));
        app.setDisplayMode(width, height, true);
        app.setShowFPS(false);
        app.setTargetFrameRate(60);
        app.setVSync(true);
        app.start();
    }

    public void initGame(GameContainer container) throws SlickException {
        container.setFullscreen(true);
        try {
            context.init(container);
        } catch (IOException | FontFormatException | SAXException | ParserConfigurationException | SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        initGame(container);
        addState(new KickState());
        addState(new GameIntroState());
        addState(new MainMenuState());
        addState(new PrepareGameState());
        addState(new PlayState());
    }

}
