package org.cjcoders.hexfight.game.states;

import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.Player;
import org.cjcoders.hexfight.board.BoardDrawer;
import org.cjcoders.hexfight.board.TileDrawer;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class TurnState extends BasicGameState {

    private Player player;
    private Image bgImage;
    private Context context;
    private BoardDrawer boardDrawer;

    public TurnState(Player player) {
        this.player = player;
        this.context = Context.getInstance();
    }

    @Override
    public int getID() {
        return State.turnStateID(player);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        bgImage = context.resources().getImage("theme-bg");
        boardDrawer = new BoardDrawer(container, new TileDrawer());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        boardDrawer.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        boardDrawer.update(container, delta);
        Input input = container.getInput();
        if(input.isKeyDown(Input.KEY_ESCAPE)){
            game.enterState(State.PAUSE.getCode(),new EmptyTransition(), new VerticalSplitTransition());
        }
        if(input.isKeyDown(Input.KEY_S)) {
            Image screenShot = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(screenShot, 0, 0);
            File file = new File("screenShot.png");

            try {
                file.createNewFile();
                new ImageIOWriter().saveImage(screenShot,"png",new FileOutputStream(file), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
