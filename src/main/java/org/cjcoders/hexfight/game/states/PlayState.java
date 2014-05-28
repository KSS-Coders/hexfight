package org.cjcoders.hexfight.game.states;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.board.*;
import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.BoardController;
import org.cjcoders.hexfight.utils.HexCalculator;
import org.cjcoders.hexfight.utils.Point;
import org.cjcoders.hexfight.utils.TileCalculator;
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
public class PlayState extends BasicGameState {

    private Logger l = Logger.getLogger(this.getClass());

    private Image bgImage;
    private Context context;
    private BoardPanel boardPanel;
    private BoardController boardController;

    private int currentPlayer = 0;
    private int nextPlayer(){return currentPlayer++; }


    public PlayState() {
        this.context = Context.getInstance();
    }

    @Override
    public int getID() {
        return State.TURN.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        bgImage = context.resources().getImage("theme-bg");
        TileCalculator calculator = new HexCalculator(context.config().getTileSize());
        boardPanel = new BoardPanel(new BoardDrawing(new BoardDrawer(calculator, new TileDrawer(calculator)), Board.getDefault(20,30,6), container.getScreenWidth(), container.getScreenHeight()));
        boardPanel.init(container);
        boardController = new BoardController();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        boardPanel.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        boardPanel.update(container);
        Input input = container.getInput();
        if(input.isKeyDown(Input.KEY_ESCAPE)){
            game.enterState(State.PAUSE.getCode(),new EmptyTransition(), new VerticalSplitTransition());
        }
        if(input.isKeyDown(Input.KEY_S)) {
            try {
                saveScreenshot(container);
            } catch (IOException e) {
                l.error("Could not save screen shot");
            } catch(SlickException e){
                l.error("Could not make screen shot");
            }
        }
    }

    @Override
    public void mouseClicked(int button, int mx, int my, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON){
            Point p = boardPanel.getBoardCooridnates(mx, my);
            boardController.tileClicked(p);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        boardPanel.updateOffset(dx, dy);
    }

    private void saveScreenshot(GameContainer container) throws IOException, SlickException {
        Image screenShot = makeScreenShot(container);
        File file = new File("screenShot.png");
        file.createNewFile();
        new ImageIOWriter().saveImage(screenShot,"png",new FileOutputStream(file), false);
    }

    private Image makeScreenShot(GameContainer container) throws SlickException {
        Image screenShot = new Image(container.getWidth(), container.getHeight());
        container.getGraphics().copyArea(screenShot, 0, 0);
        return screenShot;
    }


}
