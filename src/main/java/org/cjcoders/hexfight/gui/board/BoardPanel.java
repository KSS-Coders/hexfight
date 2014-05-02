package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.board.*;
import org.cjcoders.hexfight.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel that contains game board.
 * This is component responsible for drawing game board on the screen.
 *
 * @author Szymon Janota
 */
public class BoardPanel extends JPanel {

    /*=============================================
        CONSTANTS
     ============================================*/
    /** Background color. */
    public static final Color BGCOLOR = Color.WHITE;
    public static final int DEFAULT_SIDE_SIZE = 20;
    /** Minimum size of single hexagon's side in pixels. */
    public static final int MIN_SIDE_SIZE = 10;
    /** Maximum size of single hexagon's side in pixels. */
    public static final int MAX_SIDE_SIZE = 60;

    /*=============================================
        FIELDS
     ============================================*/
    /** Board that will be displayed on this panel. */
    private Board board;
    private final BoardDrawer boardDrawer;
    private final TilesSettings tilesSettings;
    /** Offset of the upper left corner of the board from off screen upper left corner. */
    private Point offset = new Point(0, 0);

    public BoardPanel() {
        this.tilesSettings = new TilesSettings(DEFAULT_SIDE_SIZE);
        BoardMouseListener ml = new BoardMouseListener();
        boardDrawer = new BoardDrawer(tilesSettings);
        addMouseListener(ml);
        addMouseMotionListener(ml);
        addMouseWheelListener(ml);
    }

    /*=============================================
        METHODS
     ============================================*/
    /** Paint off screen image of board. */
    @Override
    public void paintComponent(final Graphics g){
        super.paintComponent(g);
        setBackground(BGCOLOR);
        Image boardOffScreen = boardDrawer.getOffScreen(
                -offset.x,
                -offset.y,
                Math.min(getBoardWidth() - 1, getWidth()),
                Math.min(getBoardHight() - 1, getHeight()));
        g.drawImage(boardOffScreen, 0, 0, BGCOLOR, null);
    }
    private Point panelToGridXY(Point panelXY){
        int x = (panelXY.x - offset.x - tilesSettings.getxPad()) / tilesSettings.getDx();
        int y = (panelXY.y - offset.y) / (2 * tilesSettings.getDy());
        if(x % 2 == 1) y = (panelXY.y - offset.y - tilesSettings.getDy()) / (2 * tilesSettings.getDy());
        return new Point(x, y);
    }
    private int getBoardWidth(){
        return tilesSettings.calculateBoardWidth(board.getSize().width);
    }
    private int getBoardHight(){
        return tilesSettings.calculateBoardHeight(board.getSize().height);
    }


    /*=============================================
        GETTERS AND SETTERS
     ============================================*/
    public void setBoard(Board board){
        this.board = board;
        boardDrawer.setBoard(board);
    }

    /** Mouse actions listener. Enables perform actions on board. */
    public class BoardMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        /*=============================================
            CONSTANTS
         ============================================*/
        /**
         * Step of changing hexagon's side size.
         * Current side length will be updatet with this value
         * times units to scroll from mouse wheel rotation.
         */
        public static final int SIDE_SIZE_STEP = 1;

        /*============================================
            FIELDS
         ===========================================*/
        /** Indicates that cursor is over the board. */
        private boolean inArea;
        /** Indicates that dragging is in progress. */
        private boolean dragging;
        /** Point where dragging was started. */
        private Point draggingPoint;


        /*============================================
            IMPLEMENTED LISTENERS METHODS
         ===========================================*/
        /** Select a tile. */
        @Override
        public void mouseClicked(MouseEvent e) {
            Point grid = panelToGridXY(new Point(e.getX(), e.getY()));
            boolean redraw = board.tileClicked(grid);
            if(redraw){
                boardDrawer.generateOffScreen();
            }
            repaint();

        }

        /** Begin dragging. */
        @Override
        public void mousePressed(MouseEvent e){
            draggingPoint = new Point(e.getX(), e.getY());
            dragging = true;
        }

        /** Stop dragging */
        @Override
        public void mouseReleased(MouseEvent e){
            dragging = false;
        }

        /** Set cursor in range */
        @Override
        public void mouseEntered(MouseEvent e) {
            inArea = true;
        }

        /** Set cursor out of range */
        @Override
        public void mouseExited(MouseEvent e) {
            inArea = false;
        }

        /** Drag board. Change its offset according to dragging start point and current point. */
        @Override
        public void mouseDragged(MouseEvent e){
            if(dragging && inArea){
                int x = e.getX();
                int y = e.getY();
                int xOffset = x - draggingPoint.x;
                int yOffset = y - draggingPoint.y;
                updateOffset(xOffset, yOffset);
                draggingPoint = new Point(x,y);
                repaint();
            }
        }

        /** Change tile size on mouse wheel rotation. */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int toChange = e.getUnitsToScroll();
            int ns = tilesSettings.getSideSize();
            ns += toChange * SIDE_SIZE_STEP;
            if(ns < MIN_SIDE_SIZE || ns > MAX_SIDE_SIZE) return;
            tilesSettings.setSideSize(ns);
            repaint();
        }

        /*============================================
            UNIMPLEMENTED LISTENERS METHODS
         ===========================================*/
        @Override
        public void mouseMoved(MouseEvent e) {}
    }

    public void setOffset(Point offset) {
        if(fitsScreenVertically()) offset = offset.setX(this.offset.x);
        else offset = applyMinOffsetX(applyMaxOffsetX(offset));
        if(fitsScreenHorizontally()) offset = offset.setY(this.offset.y);
        else offset = applyMaxOffsetY(applyMinOffsetY(offset));
        this.offset = offset;
    }
    public void updateOffset(int xOffset, int yOffset){
        setOffset(new Point(offset.x + xOffset, offset.y + yOffset));
    }

    private boolean fitsScreenVertically(){ return getWidth() > tilesSettings.calculateBoardWidth(board.getSize().width); }
    private boolean fitsScreenHorizontally(){ return getHeight() > tilesSettings.calculateBoardHeight(board.getSize().height); }
    private Point applyMinOffsetX(Point offset){
        if(offset.x > 0) return offset.setX(0);
        return offset;
    }
    private Point applyMaxOffsetX(Point offset){
        int maxX = tilesSettings.calculateBoardWidth(board.getSize().width) - getWidth();
        if(-offset.x > maxX) return offset.setX(-maxX);
        return offset;
    }
    private Point applyMinOffsetY(Point offset){
        if(offset.y > 0) return offset.setY(0);
        return offset;
    }
    private Point applyMaxOffsetY(Point offset){
        int maxY = tilesSettings.calculateBoardHeight(board.getSize().height) - getHeight();
        if(-offset.y > maxY) return offset.setY(-maxY);
        return offset;
    }
}
