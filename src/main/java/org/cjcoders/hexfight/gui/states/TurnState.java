package org.cjcoders.hexfight.gui.states;

import org.cjcoders.hexfight.Board;
import org.cjcoders.hexfight.Player;
import org.cjcoders.hexfight.Tile;
import org.cjcoders.hexfight.gui.board.BoardDrawer;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
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
    private Board board;
    private BoardDrawer boardDrawer;

    private int side = 50;


    float scale = 2;
    public  Image bgImg;


//    @Override
//    public void mouseWheelMoved(int change){
//        if(change > 0) {
//            if (scale < 3) scale *= 1.1;
//        }else if(scale > 1) scale /= 1.1;
//        tileRed = (Polygon) tileRed.transform(Transform.createScaleTransform(scale, scale));
//    }

    public TurnState(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    @Override
    public int getID() {
        return State.turnStateID(player);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.boardDrawer = new BoardDrawer(container, 1920, 1080, 50, 2, board);
        boardDrawer.init();
        bgImg = Resources.get().images.get("turn-bg");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImg, 0, 0);
        boardDrawer.render(container, g);
        g.drawString(""+scale,1800,1000);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
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
