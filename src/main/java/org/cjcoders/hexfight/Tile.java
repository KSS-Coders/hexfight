package org.cjcoders.hexfight;

import org.cjcoders.hexfight.gui.board.BoardDrawer;
import org.cjcoders.hexfight.gui.board.Hexagon;
import org.cjcoders.hexfight.gui.states.TurnState;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.cjcoders.hexfight.old.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

import java.util.Random;

/**
* Created by mrakr_000 on 2014-05-13.
*/
public class Tile {
    private BoardDrawer turnState;
    int side;
    private int i;
    private int j;
    float scale;
    int value;
    private Image img;
    Color c;

    public Tile(BoardDrawer turnState, int i, int j, int side, float scale, Color c, int value, Image img){
        this.turnState = turnState;
        this.i = i;
        this.j = j;
        this.scale = scale;
        this.side = side;
        this.c = c;
        this.value = value;
        this.img = img;
    }
    public void render(GUIContext container, Graphics g){
        img.drawEmbedded((float) (turnState.offset.x+i*0.75*side*scale+15), (float) (turnState.offset.y+(j + 0.5*(i%2))*side*scale+15),72,72);
        g.drawImage(turnState.tileBorder.getScaledCopy(scale), (float) (turnState.offset.x+i*0.75*side*scale), (float) (turnState.offset.y+(j + 0.5*(i%2))*side*scale), c);

        g.drawString(""+value, (float) (turnState.offset.x+i*0.75*side*scale + (side*scale - g.getFont().getWidth("" + value))/2+2), (float) (turnState.offset.y +(j + 0.5*(i%2))*side*scale+ (side*scale -  g.getFont().getHeight("" + value))/2+1));
    }
}
