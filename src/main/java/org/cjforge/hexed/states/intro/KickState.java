package org.cjforge.hexed.states.intro;

import org.cjforge.hexed.states.State;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class KickState extends BasicGameState {
    @Override
    public int getID() {
        return State.KICK.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        game.enterState(State.GAME_INTRO.getCode(), new EmptyTransition(), new FadeInTransition());
    }
}
