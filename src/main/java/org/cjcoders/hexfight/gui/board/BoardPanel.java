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

    public BoardPanel() {
        this.tilesSettings = new TilesSettings(DEFAULT_SIDE_SIZE, new Point(0, 0));
        BoardMouseListener ml = new BoardMouseListener();
        boardDrawer = new BoardDrawer(tilesSettings);
        boardDrawer.setBoard(board);
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
        //Image boardOffScreen = board.getOffScreen();
        //g.drawImage(boardOffScreen, 0, 0, BGCOLOR, null);
        boardDrawer.paintBoard(g, new DrawMeImpl(g));
    }
    private Point panelToGridXY(Point panelXY){
        int x = (panelXY.x + tilesSettings.getOffset().x - tilesSettings.getxPad()) / tilesSettings.getDx();
        int y = (panelXY.y + tilesSettings.getOffset().y) / (2 * tilesSettings.getDy());
        if(x % 2 == 1) y = (panelXY.y + tilesSettings.getOffset().y - tilesSettings.getDy()) / (2 * tilesSettings.getDy());
        return new Point(x, y);
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
                tilesSettings.updateOffset(draggingPoint.x - x, draggingPoint.y - y);
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

    class DrawMeImpl implements DrawMe{
        private Graphics g;
        DrawMeImpl(Graphics g){ this.g = g; }
        @Override
        public void draw() {
            setBackground(BGCOLOR);
            BoardPanel.super.paintComponent(g);
        }
    }
}
