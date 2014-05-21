package org.cjcoders.hexfight.gui.board;

import org.cjcoders.hexfight.Board;
import org.cjcoders.hexfight.Tile;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.cjcoders.hexfight.old.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrakr_000 on 2014-05-15.
 */
public class BoardDrawer extends MouseOverArea{

    private Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow};

    public BoardDrawer(GUIContext c) throws SlickException {
        super(c, new Image(0,0), 0, 0);
    }

    public void init(){

    }

    private int getRandNum() {
        int min = 0;
        int max = 100;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private Color getRandColor() {
        int min = 0;
        int max = 10;
        int randVal = min + (int)(Math.random() * ((max - min) + 1));
        if(randVal < colors.length) return colors[randVal];
        else return Color.transparent;
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    }

    public void render(GUIContext container, Graphics g){
    }

}
