package org.cjcoders.hexfight.gui;

import org.apache.log4j.BasicConfigurator;
import org.cjcoders.hexfight.Context;
import org.cjcoders.hexfight.gui.states.GameIntroState;
import org.cjcoders.hexfight.gui.states.KickState;
import org.cjcoders.hexfight.gui.states.MainMenuState;
import org.cjcoders.hexfight.gui.states.PrepareGameState;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
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

    public static final String GAME_TITLE = "cubic";

    private Context context;

    public GameCore(String title) {
        super(title);
        context = new Context();
    }

    public void initGame(GameContainer container){
        try {
            context.init(container);
            Resources.get().fonts.load("cubic", "fonts/cubic.ttf");
            Resources.get().fonts.load("forces-squared", "fonts/forces-squared.ttf");
        } catch (IOException | FontFormatException | SAXException | ParserConfigurationException | SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void preUpdateState(GameContainer container, int delta) throws SlickException {
        super.preUpdateState(container, delta);
        try {
            context.update(container);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        initGame(container);
        addState(new KickState());
        addState(new GameIntroState(context));
        addState(new MainMenuState(context));
        addState(new PrepareGameState(context));
    }

    public static void main(String args[]) throws SlickException {
        BasicConfigurator.configure();
        AppGameContainer app = new AppGameContainer(new GameCore("Hexfight"));
        //app.setShowFPS(false);
        app.setDisplayMode(1920, 1080, true);
        //app.setTargetFrameRate(60);
        app.setVSync(true);
        app.start();
    }

}
