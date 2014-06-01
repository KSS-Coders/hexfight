package org.cjcoders.hexfight.game;

import org.apache.log4j.BasicConfigurator;
import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.states.*;
import org.cjcoders.hexfight.context.resources.Resources;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class GameCore extends StateBasedGame{

    public static final String GAME_TITLE = "Hexed";

    private Context context;

    public GameCore(String title) {
        super(title);
        context = Context.getInstance();
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

}
