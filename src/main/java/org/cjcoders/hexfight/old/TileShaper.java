package org.cjcoders.hexfight.old;

import org.cjcoders.hexfight.old.utils.Point;

import java.awt.*;

/**
 * Created by mrakr_000 on 03.05.14.
 */
public interface TileShaper {
    Point convPanelToGrid(Point panelXY);
    Point convGridToPanel(Point gridXY);

    int calculateBoardWidth(int columns);

    int calculateBoardHeight(int rows);

    Shape formTile(Point point);

    void setScale(double scale);

    double getScale();
}
