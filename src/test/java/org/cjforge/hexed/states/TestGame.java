package org.cjforge.hexed.states;

import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.BasicConfigurator;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.intro.GameIntroState;
import org.cjforge.hexed.states.intro.KickState;
import org.cjforge.hexed.states.intro.PrepareGameState;
import org.cjforge.hexed.states.menu.MainMenuState;
import org.cjforge.hexed.states.play.PlayState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.InputAdapter;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class TestGame extends StateBasedGame {

    private Context context;
    private GameState[] states;

    private TestGame(String title, GameState[] states) {
        super(title);
        this.states = states;
        context = Context.getInstance();
    }

    public static void startGame(String title, GameState[] states) throws SlickException {
        BasicConfigurator.configure();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        AppGameContainer app = new AppGameContainer(new TestGame(title, states));
        app.setDisplayMode(width, height, true);
        app.setShowFPS(false);
        app.setTargetFrameRate(60);
        app.setVSync(true);
        app.start();
    }

    public void initGame(GameContainer container) throws SlickException {
        container.getInput().addKeyListener(new InputAdapter() {
            @Override
            public void keyReleased(int key, char c) {
                if(key == Input.KEY_ESCAPE) {
                    System.exit(0);
                }
            }
        });
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
        for(GameState state : states) {
            addState(state);
        }
    }
}
