package org.cjforge.hexed.states.play.hud;

import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.game.Player;
import org.cjforge.hexed.utils.components.ButtonAction;
import org.cjforge.hexed.utils.components.TextButton;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class Hud {

    private TextButton nextTurn;
    private Player currentPlayer;

    public Hud(GameContainer container, StateBasedGame game) throws SlickException {
        Font font = Context.getInstance().resources().getFont("cubic", 32);
        nextTurn = new TextButton(container, font, "end turn", (int) (container.getWidth() * 0.1), (int) (container.getHeight() * 0.9), game);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.drawString("Player : " + currentPlayer.getID(),  (int) (container.getWidth() * 0.1), (int) (container.getHeight() * 0.1));
        nextTurn.render(container, g);
    }

    public boolean update(GameContainer container, StateBasedGame game, int delta) {
        return true;
    }

    public boolean mouseClicked(int button, int x, int y, int clickCount) {
        return false;
    }

    public void setup(final Gameplay gameplay) {
        currentPlayer = gameplay.getCurrentPlayer();
        nextTurn.addAction(new ButtonAction() {
            @Override
            public void performAction() {
                currentPlayer = gameplay.getNextPlayer();
            }
        });
        nextTurn.setEnabled(true);
    }
}
