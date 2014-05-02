package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.board.Board;
import org.cjcoders.hexfight.board.Tile;
import org.cjcoders.hexfight.utils.Point;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mrakr_000 on 30.04.14.
 */
public class BoardDrawer {



    private Board board;
    private final TileDrawer tileDrawer;


    public BoardDrawer(TilesSettings tilesSettings){
        tileDrawer = new TileDrawer(tilesSettings);
    }

    public void paintBoard(Graphics g, DrawMe callback){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        callback.draw();
        for(int h = 0; h < board.getSize().height; ++h){
            for(int w = 0; w < board.getSize().width; ++w){
                Point p = new Point(w, h);
                Tile tile = board.getTile(w, h);
                tileDrawer.drawTile(tile, p, g2);
            }
        }
    }

    void generateOffScreen() {
        // TODO Generate off screen
    }




    public void setBoard(Board board) {
        this.board = board;
    }
}
