package org.cjcoders.hexfight;

import org.cjcoders.hexfight.gui.board.BoardDrawer;
import org.cjcoders.hexfight.gui.board.Hexagon;
import org.cjcoders.hexfight.gui.states.TurnState;
import org.cjcoders.hexfight.gui.utils.resources.Resources;
import org.cjcoders.hexfight.old.utils.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import java.util.Random;

/**
* Created by mrakr_000 on 2014-05-13.
*/
public class Tile extends MouseOverArea{

    public Tile(GUIContext container, Image image, int x, int y) {
        super(container, image, x, y);
    }

}
