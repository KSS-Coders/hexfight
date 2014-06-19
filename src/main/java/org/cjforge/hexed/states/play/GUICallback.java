package org.cjforge.hexed.states.play;

import org.cjforge.hexed.utils.Point;

/**
 * Created by mrakr_000 on 2014-06-02.
 */
public interface GUICallback {
    public Integer askForForcesCount(Integer min, Integer max);

    public Point getClickCoordinates();
}
