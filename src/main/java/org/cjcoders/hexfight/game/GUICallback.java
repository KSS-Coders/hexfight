package org.cjcoders.hexfight.game;

import org.cjcoders.hexfight.utils.Point;

/**
 * Created by mrakr_000 on 2014-06-02.
 */
public interface GUICallback {
    public Integer askForInt(String msg, Integer max, Integer min);
    public Point getClickCoordinates();
}
