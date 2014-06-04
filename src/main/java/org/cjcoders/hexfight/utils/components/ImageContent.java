package org.cjcoders.hexfight.utils.components;

import org.cjcoders.hexfight.game.states.PlayState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
* Created by mrakr_000 on 2014-06-04.
*/
public class ImageContent implements Content {
    private Image img;

    public ImageContent(Image img) {
        this.img = img;
    }


        @Override
    public void render(GUIContext container, Graphics g, Rectangle visibleArea) {
        g.drawImage(img, visibleArea.getX(), visibleArea.getY());
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public int getCenterX() {
        return img.getWidth()/2;
    }

    @Override
    public int getCenterY() {
        return img.getHeight()/2;
    }
}
