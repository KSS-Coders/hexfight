package org.cjforge.hexed.states.intro;

import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.game.BoardFactory;
import org.cjforge.hexed.game.GameBoard;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.game.Player;
import org.cjforge.hexed.states.State;
import org.cjforge.hexed.states.play.PlayState;
import org.cjforge.hexed.utils.HexCalculator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The state to set up game configuration. Right now the configuration is hardcoded and cannot be
 * changed non-programatically. the only action of this game is to prepare board and gameplay objects
 * and pass it to PlayState.
 */
public class PrepareGameState extends BasicGameState {
    public PrepareGameState() {
    }

    @Override
    public int getID() {
        return State.PREPARE_GAME.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
//        PlayState play = (PlayState) game.getState(State.TURN.getCode());
//        int playersCount = Context.getInstance().config().getPlayersNumber();
//        play.setUp(getPlayers(playersCount));
//        game.enterState(State.TURN.getCode());
        PlayState play = (PlayState) game.getState(State.TURN.getCode());
        int playersCount = Context.getInstance().config().getPlayersNumber();
        Player[] players = getPlayers(playersCount);
        GameBoard gameBoard = new BoardFactory().buildSimpleBoard(10, 10, players);
        play.setup(new Gameplay(gameBoard, players, new HexCalculator(Context.getInstance().config().getTileSize())));
        game.enterState(State.TURN.getCode());
    }

    private Player[] getPlayers(int playersCount) {
        Player[] players = new Player[playersCount];
        for (int i = 0; i < playersCount; ++i) players[i] = new Player(i);
        return players;
    }


}
