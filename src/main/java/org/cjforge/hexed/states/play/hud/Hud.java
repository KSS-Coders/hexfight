package org.cjforge.hexed.states.play.hud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.game.Player;
import org.cjforge.hexed.game.PlayerColors;
import org.cjforge.hexed.utils.ColorFill;
import org.cjforge.hexed.utils.Hexagon;
import org.cjforge.hexed.utils.SlickColors;
import org.cjforge.hexed.utils.components.ButtonAction;
import org.cjforge.hexed.utils.components.TextButton;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class Hud {

    private Logger log = LogManager.getLogger(this.getClass());

    private TextButton nextTurn;
    private Player currentPlayer;
    private Shape hudBackground;

    private Font playerFont;

    private Map<Player, ColorFill> playerColors;

    public Hud(GameContainer container, StateBasedGame game) throws SlickException {
        Font font = Context.getInstance().resources().getFont("cubic", 32);
        Dimension screenResolution = Context.getInstance().config().getScreenResolution();
        playerColors = new HashMap<>();
        playerFont = Context.getInstance().resources().getFont("forces-squared", 32);
//        hudBackground = new Rectangle(0,0,screenResolution.width * 0.2f, screenResolution.height );
        hudBackground = new Hexagon(screenResolution.height, (int) (-screenResolution.height + getHudWidth()), 0);
        nextTurn = new TextButton(container, font, "end turn", (int) (container.getWidth() * 0.02), (int) (container.getHeight() * 0.7), game);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.fill(hudBackground, getCurrentPlayerColor());
//        playerFont.drawString((int) (container.getWidth() * 0.02), (int) (container.getHeight() * 0.3), "Player : " + currentPlayer.getID(), Color.black);

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

    private ColorFill getCurrentPlayerColor() {
        if(playerColors.containsKey(currentPlayer)) {
            return playerColors.get(currentPlayer);
        } else {
            Color playerColor = PlayerColors.getColorForPlayer(currentPlayer);
            Color bightened = SlickColors.ensurePositiveChanels(playerColor);
//            log.debug("Player " + currentPlayer.getID() + " has color " + playerColor + ". Brightened it's " +  bightened.brighter().brighter());
            ColorFill fill = new ColorFill(bightened.brighter().brighter());
            playerColors.put(currentPlayer, fill);
            return fill;
        }
    }

    public float getHudWidth() {
        return Context.getInstance().config().getScreenResolution().height * 0.4f;
    }
}
