package org.cjcoders.hexfight.gui.states;

import org.cjcoders.hexfight.Board;
import org.cjcoders.hexfight.Context;
import org.cjcoders.hexfight.Player;
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
    public PrepareGameState(Context context) {
        this.context = context;
    }

    @Override
    public int getID() {
        return State.PREPARE_GAME.getCode();
    }

    private Player firstPlayer;
    private Board board;
    private Image bg;
    private Context context;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        try{
            game.getState(State.turnStateID(firstPlayer)).init(container, game);
        }
        catch (NullPointerException e){
            firstPlayer = new Player(1);
            bg = context.resources().getImage("menu-bg");
            board = new Board(30, 30);

            TurnState state = new TurnState(firstPlayer, context);
            state.init(container, game);
            game.addState(state);
        }
        game.enterState(State.turnStateID(firstPlayer));
    }


}
