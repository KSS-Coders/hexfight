package org.cjforge.hexed.states.play;

import org.cjforge.hexed.utils.Point;

/**
 * This interface show which methods should be available for game logic to check GUI state or ask player for more input.
 */
public interface GUICallback {
    public Integer askForForcesCount(Integer min, Integer max);

    public Point getClickCoordinates();
}
