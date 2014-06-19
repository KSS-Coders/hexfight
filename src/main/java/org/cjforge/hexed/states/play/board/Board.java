package org.cjforge.hexed.states.play.board;

import org.cjforge.hexed.context.Context;
import org.cjforge.hexed.game.Gameplay;
import org.cjforge.hexed.states.play.GUICallback;
import org.cjforge.hexed.states.play.GUIRequest;
import org.cjforge.hexed.utils.Point;
import org.cjforge.hexed.utils.components.Content;
import org.cjforge.hexed.utils.components.DraggableContainer;
import org.cjforge.hexed.utils.components.ImageContent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by mrakr_000 on 2014-06-19.
 */
public class Board extends DraggableContainer{
    private final ForcesCountPrompt dialog;
    private Gameplay gameplay;
    private BoardDrawer boardDrawer;

    public Board(GameContainer container, StateBasedGame game) throws SlickException {
        super(0,0,0,0);
        Context context = Context.getInstance();
        Image bgImage = context.resources().getImage("theme-bg");
        setWidth(container.getScreenWidth());
        setHeight(container.getScreenHeight());
        setPaddingX(50);
        setPaddingY(50);
        setBackground(new ImageContent(bgImage));
        dialog = new ForcesCountPrompt(container, game, 200, 200);
    }

    public void setup(Gameplay gameplay) {
        this.gameplay = gameplay;
        TileDrawer td = new MainTileDrawer(gameplay.getCalculator());
        boardDrawer = new BoardDrawer(gameplay.getCalculator(), td);
        Content bdg = new BoardDrawing(boardDrawer, gameplay.getGameBoard());
        setDrawing(bdg);
    }

    @Override
    public void update(GUIContext context, Game game, int delta) {
        Input input = context.getInput();
    }

    public void mouseClicked(int button, final int mx, final int my, int clickCount){
        if(!hasPinned() && button == Input.MOUSE_LEFT_BUTTON){
            final Point p = getAbsoluteCooridnates(mx, my);
            final Point p1 = boardDrawer.getBoardCooridnates(p.x, p.y);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    gameplay.getBoardController().tileClicked(p1, new BoardGUICallback(new Point(mx, my)));
                }
            }).start();
        }
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        int dx = newx - oldx;
        int dy = newy - oldy;
        drag(dx, dy);
    }


    private class BoardGUICallback implements GUICallback {

        private Point clickCoordinates;

        private BoardGUICallback(Point clickCoordinates) {
            this.clickCoordinates = clickCoordinates;
        }

        @Override
        public Integer askForForcesCount(Integer min, Integer max) {
            GUIRequest<Integer> request = dialog.startRequest("Ships to be", "moved", min, max);
            pin(dialog, clickCoordinates.x, clickCoordinates.y);
            try {
                request.blockUntilFinished();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unpin();
            return request.getResult();
        }

        @Override
        public Point getClickCoordinates() {
            return clickCoordinates;
        }
    }
}
