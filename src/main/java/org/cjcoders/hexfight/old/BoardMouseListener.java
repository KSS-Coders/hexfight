package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.Point;

import java.awt.event.*;

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
    public static final double SIDE_SIZE_STEP = 0.02;
    private final OffScreenPosition position;

    /*============================================
        FIELDS
     ===========================================*/
    private BoardDrawingAssistant assistant;
    /** Indicates that cursor is over the board. */
    private boolean inArea;
    /** Indicates that dragging is in progress. */
    private boolean dragging;
    /** Point where dragging was started. */
    private Point draggingPoint;

    public BoardMouseListener(BoardDrawingAssistant assistant, OffScreenPosition position) {
        this.assistant = assistant;
        this.position = position;
    }


    /*============================================
        IMPLEMENTED LISTENERS METHODS
     ===========================================*/
    /** Select a tile. */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point grid = position.removeOffset(new Point(e.getX(), e.getY()));
        assistant.tileClicked(grid);
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
            position.updateOffset(xOffset, yOffset);
            draggingPoint = new Point(x,y);
        }
    }

    /** Change tile size on mouse wheel rotation. */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Spinning");
        int toChange = e.getUnitsToScroll();
        double ns = assistant.getScale();
        ns += toChange * SIDE_SIZE_STEP;
        System.out.println(ns);
        //if(ns < BoardPanel.MIN_SIDE_SIZE || ns > BoardPanel.MAX_SIDE_SIZE) return;
        assistant.setScale(ns);
        position.setOffset(position.getOffset());
    }

    /*============================================
        UNIMPLEMENTED LISTENERS METHODS
     ===========================================*/
    @Override
    public void mouseMoved(MouseEvent e) {}
}
