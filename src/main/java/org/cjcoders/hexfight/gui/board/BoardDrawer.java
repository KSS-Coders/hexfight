package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.board.Board;
import org.cjcoders.hexfight.board.Tile;
import org.cjcoders.hexfight.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by mrakr_000 on 30.04.14.
 */
public class BoardDrawer implements ChangeListener{

    private Board board;
    private final TileDrawer tileDrawer;
    private BufferedImage offScreen;
    private TilesSettings tilesSettings;

    public BoardDrawer(TilesSettings tilesSettings){
        tileDrawer = new TileDrawer(tilesSettings);
        this.tilesSettings = tilesSettings;
        tilesSettings.addChangeListener(this);
    }

    public void paintBoard(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        for(int h = 0; h < board.getSize().height; ++h){
            for(int w = 0; w < board.getSize().width; ++w){
                Point p = new Point(w, h);
                Tile tile = board.getTile(w, h);
                tileDrawer.drawTile(tile, p, g2);
            }
        }
    }

    // TODO Return generated off screen image
    public Image getOffScreen() {
        return offScreen;
    }

    void generateOffScreen() {
        offScreen = new BufferedImage((int)(1.1 * getBoardWidth()), (int)(1.1 * getBoardHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = offScreen.createGraphics();
        paintBoard(g);
    }

    public Integer getBoardHeight(){
        return tilesSettings.calculateBoardHeight(board.getSize().height);
    }
    public Integer getBoardWidth(){
        return tilesSettings.calculateBoardWidth(board.getSize().width);
    }



    public void setBoard(Board board) {
        this.board = board;
        generateOffScreen();
    }

    @Override
    public void update(int changedValue) {
        if(changedValue == TilesSettings.SIDE_SIZE_CHANGED)generateOffScreen();
    }
}
