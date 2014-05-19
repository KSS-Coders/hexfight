package org.cjcoders.hexfight.gui;

import org.cjcoders.hexfight.gui.states.GameIntroState;
import org.cjcoders.hexfight.gui.states.KickState;
import org.cjcoders.hexfight.gui.states.MainMenuState;
import org.cjcoders.hexfight.gui.states.PrepareGameState;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class GameCore extends StateBasedGame{

    public static final String GAME_TITLE = "cubic";

    public GameCore(String title) {
        super(title);
    }

    public static void initResources(){
        try {
            Resources.get().fonts.load("cubic", "fonts/cubic.ttf");
            Resources.get().fonts.load("forces-squared", "fonts/forces-squared.ttf");
            Resources.get().images.load("menu-bg", "images/1920/menu-bg.jpg");
            Resources.get().images.load("logo", "images/logo.png");
            Resources.get().images.load("logo-mini", "images/logo-mini.png");
            Resources.get().images.load("hex-border", "images/hex-border.png");
            Resources.get().images.load("turn-bg", "images/themes/space/bg.jpg");
            Resources.get().images.load("planet", "images/themes/space/tiles/planet4.png");
            Resources.get().images.load("tiles", "images/themes/space/tiles.png");
        } catch (IOException | FontFormatException | SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        initResources();
        addState(new KickState());
        addState(new GameIntroState());
        addState(new MainMenuState());
        addState(new PrepareGameState());
    }

    public static void main(String args[]) throws SlickException {
        AppGameContainer app = new AppGameContainer(new GameCore("Hexfight"));
        //app.setShowFPS(false);
        app.setDisplayMode(1920, 1080, true);
        //app.setTargetFrameRate(60);
        app.setVSync(true);
        app.start();
    }

}
