package org.cjcoders.hexfight.game.states;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cjcoders.hexfight.board.*;
import org.cjcoders.hexfight.context.Context;
import org.cjcoders.hexfight.game.BoardController;
import org.cjcoders.hexfight.game.GUICallback;
import org.cjcoders.hexfight.game.GUIRequest;
import org.cjcoders.hexfight.utils.HexCalculator;
import org.cjcoders.hexfight.utils.Point;
import org.cjcoders.hexfight.utils.TileCalculator;
import org.cjcoders.hexfight.utils.components.DialogBox;
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

    private DialogBox dialog;

    private boolean leftMouseButtonClicked;
    private Point leftMouseButtonClikcCoordinates;

    private int currentPlayer = 0;
    private int nextPlayer(){return currentPlayer++; }


    public PlayState() {
        this.context = Context.getInstance();
    }


    public void showDialog(DialogBox dialog){
        this.dialog = dialog;
    }
    public void cleanDialog(){
        this.dialog = null;
    }

    @Override
    public int getID() {
        return State.TURN.getCode();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        l.setLevel(Level.DEBUG);
        l.debug("Initializing PlayState");
        bgImage = context.resources().getImage("theme-bg");
        TileCalculator calculator = new HexCalculator(context.config().getTileSize());
        Board board = Board.getDefault(20,30,6);
        l.debug("New TileDrawer");
        TileDrawer td = new TileDrawer(calculator);
        l.debug("New BoardDrawer");
        BoardDrawer bd = new BoardDrawer(calculator, td);
        l.debug("New BoardDrawing");
        BoardDrawing bdg = new BoardDrawing(bd, board, container.getScreenWidth(), container.getScreenHeight());
        l.debug("New BoardPanel");
        boardPanel = new BoardPanel(bdg);
        l.debug("BoardPanel Init");
        boardPanel.init(container);
        l.debug("new BoardController");
        boardController = new BoardController(board);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        boardPanel.render(container, g);
        if(dialog != null && dialog.isInitialized()){
            dialog.render(container, g);
        }
    }

    @Override
    public void update(final GameContainer container, final StateBasedGame game, int delta) throws SlickException {
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
        if(dialog != null && !dialog.isInitialized()){
            dialog.init(container, game, getID(), 200, 200);
        }
        if(leftMouseButtonClicked){
            final Point p = boardPanel.getBoardCooridnates(leftMouseButtonClikcCoordinates.x, leftMouseButtonClikcCoordinates.y);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boardController.tileClicked(p, new BoardGUICallback(leftMouseButtonClikcCoordinates));
                }
            }).start();
            leftMouseButtonClicked = false;
        }
    }

    @Override
    public void mouseClicked(int button, int mx, int my, int clickCount) {
        if(button == Input.MOUSE_LEFT_BUTTON && dialog == null){
            leftMouseButtonClicked = true;
            leftMouseButtonClikcCoordinates = new Point(mx, my);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        Point o1 = boardPanel.getOffset();
        boardPanel.updateOffset(dx, dy);
        if(dialog != null){
            Point o2 = boardPanel.getOffset();
            Point diff = o2.add(o1.negative());
            dialog.updateOffset(diff.x,diff.y);
        }
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
            DialogBox dialog = null;
            try {
                dialog = new DialogBox(msg, clickCoordinates.x, clickCoordinates.y, min, max);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            GUIRequest<Integer> request = dialog.getRequest();
            showDialog(dialog);
            try {
                request.blockUntilFinished();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cleanDialog();
            return request.getResult();
        }

        @Override
        public Point getClickCoordinates() {
            return clickCoordinates;
        }
    }

}
