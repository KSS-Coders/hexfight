package org.cjcoders.hexfight.board;

import org.cjcoders.hexfight.old.utils.Point;

import java.awt.*;
import java.util.List;

/**
 * Created by mrakr_000 on 27.04.14.
 */
public class Board{

    /*============================================
        CONSTANTS
     ===========================================*/
    public static final Color CELLBG = Color.ORANGE;
    public static final Color CELLBG_CLICKED = Color.RED;
    public static final Color CELLBORDER = Color.BLACK;

    /*============================================
        FIELDS
     ===========================================*/

    public Dimension getSize() {
        return size;
    }

    /** Size of the board in rows, columns. */
    private Dimension size;

    /** Two dimensional grid of tiles. */
    private List<List<Tile>> board;
    /** Indicates if tile x, y has been clicked. */
    private boolean[][] clicked;

    public Board(int rows, int cols){
        this(new Dimension(rows, cols));
    }
    public Board(Dimension size){
        this.size = size;
        clicked = new boolean[size.width][size.height];
    }

   /* private boolean shouldPrint(Point gridXY) {
        org.cjcoders.hexfight.old.utils.Point panelXY = gridToPanelXY(gridXY);
        return inRange(panelXY.x - xPad) ||
                inRange(panelXY.x + dx) ||
                inRange(panelXY.y)||
                inRange(panelXY.y + 2 * dy);
    }*/
    /*private boolean inRange(int n){
        return n > 0 && n < getHeight();
    }*/





    /**
     * Perform "Clicked" action on tile indicated by xy coordinates
     * in the grid.
     *
     * @param gridXY Coordinates of clicked tile
     * @return Boolean value indicates if off screen should be reloaded
     */
    public boolean tileClicked(Point gridXY){
        System.out.println("Tile clicked : "+ gridXY);
        clicked[gridXY.x][gridXY.y] = !clicked[gridXY.x][gridXY.y];
        return true;
    }

    public Tile getTile(int w, int h) {
        return fakeTile(clicked[w][h]);
    }

    private Tile fakeTile(boolean clicked){
        if(clicked){
            return new Tile() {
                @Override
                public Paint getFillImg() {
                    return CELLBG_CLICKED;
                }

                @Override
                public Color getBorderColor() {
                    return CELLBORDER;
                }
            };
        }
        else{
            return new Tile() {
                @Override
                public Paint getFillImg() {
                    return CELLBG;
                }

                @Override
                public Color getBorderColor() {
                    return CELLBORDER;
                }
            };
        }
    }

    public void addTileChangeListener(TileChangedListener listener) {

    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }
}
