package org.cjcoders.hexfight.game.states;

import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.utils.components.ButtonAction;
import org.cjcoders.hexfight.utils.components.TextButton;
import org.newdawn.slick.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

    private Font cubic64b;
    private Font forcedSquared48;
    private TextButton startGame;
    private TextButton exit;
    private Image bg;
    private Image logo;

    private Context context;

    public MainMenuState(){
        this.context = Context.getInstance();
    }

    @Override
    public int getID() {
        return State.MAIN_MENU.getCode();
    }

    @Override
    public void init(final GameContainer container, final StateBasedGame game) throws SlickException {
        try {
            cubic64b = context.resources().getFont("cubic", 64, "b");
            forcedSquared48 = context.resources().getFont("forces-squared", 48);
            bg =  context.resources().getImage("menu-bg");
            logo =  context.resources().getImage("logo");
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
