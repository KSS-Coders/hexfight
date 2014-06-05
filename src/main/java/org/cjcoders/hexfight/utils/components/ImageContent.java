package org.cjcoders.hexfight.utils.components;

import org.cjcoders.hexfight.game.states.PlayState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
* Created by mrakr_000 on 2014-06-04.
*/
public class ImageContent extends SimpleContent {
    private Image img;

    public ImageContent(Image img) {
        super(img.getWidth(), img.getHeight());
        this.img = img;
    }

        @Override
    public void render(GUIContext container, Graphics g, Rectangle visibleArea) {
        g.drawImage(img, visibleArea.getX(), visibleArea.getY());
    }
}
