package org.cjforge.hexed.utils.components;

import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Created by mrakr_000 on 2014-06-04.
 */
public interface GraphicComponent{
    int getWidth();

    void setWidth(Integer width);

    int getHeight();

    void init(GUIContext context, Game game);

    void update(GUIContext context, Game game, int delta);

    void render(GUIContext context, Game game, Graphics g);

    void setHeight(Integer height);
}
