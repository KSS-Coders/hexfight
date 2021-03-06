package org.cjforge.hexed.states.menu;

import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.states.State;
import org.cjforge.hexed.utils.components.ButtonAction;
import org.cjforge.hexed.utils.components.TextButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Main Menu of the game.
 */
public class MainMenuState extends BasicGameState {
    private Font forcedSquared48;
    private TextButton startGame;
    private TextButton exit;
    private Image bg;
    private Image logo;

    private Context context;

    public MainMenuState() {
        this.context = Context.getInstance();
    }

    @Override
    public int getID() {
        return State.MAIN_MENU.getCode();
    }

    @Override
    public void init(final GameContainer container, final StateBasedGame game) throws SlickException {
        try {
            forcedSquared48 = context.resources().getFont("forces-squared", 48);
            bg = context.resources().getImage("menu-bg");
            logo = context.resources().getImage("logo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String buttonText = "Start game";
        startGame = new TextButton(container, forcedSquared48, buttonText, (container.getWidth() - forcedSquared48.getWidth(buttonText)) / 2, 300, game);
        exit = new TextButton(container, forcedSquared48, "Exit", (int) (container.getWidth() * 0.9), (int) (container.getHeight() * 0.9), game);
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
        g.drawImage(logo, (container.getWidth() - logo.getWidth()) / 2, (float) (container.getHeight() * 0.1));
        startGame.render(container, g);
        exit.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        exit.setEnabled(false);
        startGame.setEnabled(false);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        exit.setEnabled(true);
        startGame.setEnabled(true);
    }
}
