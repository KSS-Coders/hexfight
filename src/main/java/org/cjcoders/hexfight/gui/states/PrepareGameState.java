package org.cjcoders.hexfight.gui.states;

import org.cjcoders.hexfight.Board;
import org.cjcoders.hexfight.Player;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class PrepareGameState extends BasicGameState{
    @Override
    public int getID() {
        return State.PREPARE_GAME.getCode();
    }

    Player firstPlayer;
    Board board;
    Image bg;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        firstPlayer = new Player(1);
        bg = Resources.get().images.get("menu-bg");
        board = new Board(30, 30);
        TurnState state = new TurnState(firstPlayer);
        state.init(container, game);
        game.addState(state);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        game.enterState(State.turnStateID(firstPlayer));
    }


}
