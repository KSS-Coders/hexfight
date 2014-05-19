package org.cjcoders.hexfight.gui.states;

import org.cjcoders.hexfight.gui.board.Hexagon;
import org.cjcoders.hexfight.gui.utils.ButtonAction;
import org.cjcoders.hexfight.gui.utils.TextButton;
import org.cjcoders.hexfight.gui.utils.fonts.CustomizableFont;
import org.cjcoders.hexfight.gui.utils.resources.FontsManager;
import org.cjcoders.hexfight.gui.utils.resources.ImagesManager;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-05-10.
 */
public class MainMenuState extends BasicGameState {
    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */

    Font cubic64b;
    Font forcedSquared48;
    TextButton startGame;
    TextButton exit;
    Image bg;
    Image logo;

    public MainMenuState(){}

    @Override
    public int getID() {
        return State.MAIN_MENU.getCode();
    }

    @Override
    public void init(final GameContainer container, final StateBasedGame game) throws SlickException {

        try {
            cubic64b = Resources.get().fonts.get("cubic").withSize(64).withStyle(CustomizableFont.BOLD).get();
            forcedSquared48 = Resources.get().fonts.get("forces-squared").getWithSize(48f);
            bg =  Resources.get().images.get("menu-bg");
            logo =  Resources.get().images.get("logo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String buttonText = "Start game";
        startGame = new TextButton(container, forcedSquared48, buttonText, (container.getWidth() - forcedSquared48.getWidth(buttonText))/2, 300, game, getID());
        exit = new TextButton(container, forcedSquared48, "Exit", (int) (container.getWidth()*0.9), (int) (container.getHeight()*0.9), game, getID());
        exit.setEnabled(true);
        startGame.setEnabled(true);
        exit.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                container.exit();
            }
        });
        startGame.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                game.enterState(State.PREPARE_GAME.getCode());
            }
        });
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bg, 0, 0);
        g.drawImage(logo,(container.getWidth() - logo.getWidth())/2, (float) (container.getHeight() * 0.1));
        startGame.render(container, g);
        exit.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

}
