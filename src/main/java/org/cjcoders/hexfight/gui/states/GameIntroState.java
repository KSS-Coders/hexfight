package org.cjcoders.hexfight.gui.states;

import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class GameIntroState extends BasicGameState {

    private static final int INTRO_DURATION = 5000;

    private int timePassed;
    private Image logoMini;
    private Font forcesSquared18;

    @Override
    public int getID() {
        return State.GAME_INTRO.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        logoMini = Resources.get().images.get("logo-mini");
        forcesSquared18 = Resources.get().fonts.get("forces-squared").getWithSize(18);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(logoMini, (container.getWidth() - logoMini.getWidth())/2, (float) (container.getHeight()* 0.2));
        String text = "This game was created by two marvelous programmers.";
        forcesSquared18.drawString((container.getWidth() - forcesSquared18.getWidth(text))/2, (float) (container.getHeight()* 0.4),text);
        text = "By grading it highly you encourage them to built more breathtaking games.";
        forcesSquared18.drawString((container.getWidth() - forcesSquared18.getWidth(text))/2, (float) (container.getHeight()* 0.4 + forcesSquared18.getHeight(text) + 5),text);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        timePassed += delta;
        if(timePassed > INTRO_DURATION) game.enterState(State.MAIN_MENU.getCode(), new FadeOutTransition(), new EmptyTransition());
    }

    @Override
    public void keyPressed(int key, char c) {
        timePassed = INTRO_DURATION;
    }
}
