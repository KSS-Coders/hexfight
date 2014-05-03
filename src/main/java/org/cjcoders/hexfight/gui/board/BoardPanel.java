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
    private Point offset;
    private int paddingX;
    private int paddingY;

    public BoardPanel() {
        this.tilesSettings = new TilesSettings(DEFAULT_SIDE_SIZE);
        BoardMouseListener ml = new BoardMouseListener();
        boardDrawer = new BoardDrawer(tilesSettings);
        addMouseListener(ml);
        addMouseMotionListener(ml);
        addMouseWheelListener(ml);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //log("Resizing");
                setOffset(offset);
            }
        });
    }

    /*=============================================
        METHODS
     ============================================*/
    /** Paint off screen image of board. */
    @Override
    public void paintComponent(final Graphics g){
        super.paintComponent(g);
        setBackground(BGCOLOR);
//        log("Offset : "+offset);
//        log("Screen size : "+ new Point(Math.min(getBoardWidth() - 1, getWidth()), Math.min(getBoardHight() - 1, getHeight())));
//        log("Image size : "+ new Point(getBoardWidth(),getBoardHight() ));
//        log("End point : "+boardDrawer.getOffScreenCenter().add(offset));
//        log("X : ");
        Image boardOffScreen = boardDrawer.getOffScreen(
                -offset.x,
                -offset.y,
                Math.min(getBoardWidth(), getWidth()),
                Math.min(getBoardHight(), getHeight()));
        g.drawImage(boardOffScreen, paddingX, paddingY, BGCOLOR, null);
    }
    private Point panelToGridXY(Point panelXY){
        int x = (panelXY.x - offset.x - tilesSettings.getxPad() - paddingX) / tilesSettings.getDx();
        int y = (panelXY.y - offset.y - paddingY) / (2 * tilesSettings.getDy());
        if(x % 2 == 1) y = (panelXY.y - offset.y - tilesSettings.getDy() - paddingY) / (2 * tilesSettings.getDy());
        return new Point(x, y);
    }
    private int getBoardWidth(){
        return tilesSettings.calculateBoardWidth(board.getSize().width);
    }
    private int getBoardHight(){
        return tilesSettings.calculateBoardHeight(board.getSize().height);
    }
    public Point getCenteredPoint(){
        return screenCenter().add(offset.negative());
    }
    public void setCenteredPoint(Point pointOnImage){
        setOffset(pointOnImage.add(screenCenter().negative()));
    }
    public void centerScreen(){
        setCenteredPoint(boardDrawer.getOffScreenCenter());
    }
    private Point screenCenter(){ return new Point(getWidth()/2, getHeight()/2); }


    /*=============================================
        GETTERS AND SETTERS
     ============================================*/
    public void setBoard(Board board){
        this.board = board;
        setOffset(new Point(0, 0));
        boardDrawer.setBoard(board);
        centerScreen();
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
            //log("Center : "+getCenteredPoint());
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
            setOffset(offset);
            repaint();
        }

        /*============================================
            UNIMPLEMENTED LISTENERS METHODS
         ===========================================*/
        @Override
        public void mouseMoved(MouseEvent e) {}
    }

    public void setOffset(Point offset) {
        //log(fitsScreenVertically());
        if(fitsScreenVertically()) offset = centerBoardX(offset);
        else{
            paddingX = 0;
            offset = applyMinOffsetX(applyMaxOffsetX(offset));
        }
        //log(fitsScreenHorizontally());
        if(fitsScreenHorizontally()) offset = centerBoardY(offset);
        else{
            paddingY = 0;
            offset = applyMaxOffsetY(applyMinOffsetY(offset));
        }
        //log(offset);
        this.offset = offset;
    }
    public void updateOffset(int xOffset, int yOffset){
        setOffset(new Point(offset.x + xOffset, offset.y + yOffset));
    }
    private Point centerBoardX(Point offset){
        paddingX = (getWidth() - getBoardWidth())/2;
        return offset.setX(0);
    }
    private Point centerBoardY(Point offset){
        paddingY = (getHeight() - getBoardHight())/2;
        return offset.setY(0);
    }


    private boolean fitsScreenVertically(){ /*log(getWidth()+" ; "+tilesSettings.calculateBoardWidth(board.getSize().width));*/ return getWidth() > tilesSettings.calculateBoardWidth(board.getSize().width); }
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
    public void log(Object o){
        System.out.println(o);
    }
}
