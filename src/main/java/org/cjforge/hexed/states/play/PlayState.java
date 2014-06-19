package org.cjforge.hexed.states.play;

import org.apache.log4j.Logger;
import org.cjforge.hexed.game.*;
import org.cjforge.hexed.states.State;
import org.cjforge.hexed.states.play.board.*;
import org.cjforge.hexed.utils.Profiler;
import org.cjforge.hexed.utils.components.Content;
import org.cjforge.hexed.utils.components.DraggableContainer;
import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.utils.HexCalculator;
import org.cjforge.hexed.utils.Point;
import org.cjforge.hexed.utils.TileCalculator;
import org.cjforge.hexed.utils.components.DialogBox;
import org.cjforge.hexed.utils.components.ImageContent;
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

    private DraggableContainer boardPanel;
    private BoardController boardController;
    private BoardDrawer boardDrawer;

    private DialogBox dialog;

    private boolean leftMouseButtonClicked;
    private Point leftMouseButtonClickCoordinates;

    private int currentPlayer = 0;
    private int nextPlayer(){return currentPlayer++; }

    public PlayState() {

    }

    public void setUp(Player[] players){
        Context context = Context.getInstance();
        TileCalculator calculator = new HexCalculator(context.config().getTileSize());
        GameBoard gameBoard = new BoardFactory().buildSimpleBoard(100,100,players);
        l.info("Board size : " + new Point(gameBoard.getGrid().rows(), gameBoard.getGrid().cols()));
        TileDrawer td = new MainTileDrawer(calculator);
        boardDrawer = new BoardDrawer(calculator, td);
        Content bdg = new BoardDrawing(boardDrawer, gameBoard);
        boardPanel.setDrawing(bdg);
        boardController = new BoardController(gameBoard);
    }

    @Override
    public int getID() {
        return State.TURN.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        boardPanel = new DraggableContainer(0, 0, 0, 0);
        Context context = Context.getInstance();
        Image bgImage = context.resources().getImage("theme-bg");
        boardPanel.setWidth(container.getScreenWidth());
        boardPanel.setHeight(container.getScreenHeight());
        boardPanel.setPaddingX(50);
        boardPanel.setPaddingY(50);
        boardPanel.setBackground(new ImageContent(bgImage));
        boardPanel.init(container, game);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Profiler p = new Profiler(this.getClass().getName(), Profiler.MICROS);
        p.start();
        p.setEnabled(false);
        boardPanel.render(container, game, g);
        p.logFromStart("render");
    }

    @Override
    public void update(final GameContainer container, final StateBasedGame game, int delta) throws SlickException {
        boardPanel.update(container, game, delta);
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
        if(leftMouseButtonClicked){
            final Point p = boardPanel.getAbsoluteCooridnates(leftMouseButtonClickCoordinates.x, leftMouseButtonClickCoordinates.y);
            final Point p1 = boardDrawer.getBoardCooridnates(p.x,p.y);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boardController.tileClicked(p1, new BoardGUICallback(leftMouseButtonClickCoordinates));
                }
            }).start();
            leftMouseButtonClicked = false;
        }
    }

    @Override
    public void mouseClicked(int button, int mx, int my, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON && !boardPanel.hasPinned()){
            leftMouseButtonClicked = true;
            leftMouseButtonClickCoordinates = new Point(mx, my);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        boardPanel.drag(dx, dy);
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


    private class BoardGUICallback implements GUICallback {

        private Point clickCoordinates;

        private BoardGUICallback(Point clickCoordinates) {
            this.clickCoordinates = clickCoordinates;
        }

        @Override
        public Integer askForInt(String msg, Integer min, Integer max) {
            GUIRequest<Integer> request = dialog.startRequest(msg, min, max);
            boardPanel.pin(dialog, clickCoordinates.x, clickCoordinates.y);
            try {
                request.blockUntilFinished();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boardPanel.unpin();
            return request.getResult();
        }

        @Override
        public Point getClickCoordinates() {
            return clickCoordinates;
        }
    }

}
