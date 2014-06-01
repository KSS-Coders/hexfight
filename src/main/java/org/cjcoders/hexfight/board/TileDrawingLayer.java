package org.cjcoders.hexfight.board;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public abstract class TileDrawingLayer {

    public void init(TileDrawing drawing){}

    public abstract void render(TileDrawing tileDrawing, GUIContext container, Graphics g, int xOffset, int yOffset);
}
