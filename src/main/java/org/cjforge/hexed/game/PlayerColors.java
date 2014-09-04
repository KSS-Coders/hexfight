package org.cjforge.hexed.game;

import org.newdawn.slick.Color;

/**
 * Created by mrakr_000 on 2014-09-04.
 */
public class PlayerColors {
    private PlayerColors(){}

    private static final Color[] colors = new Color[]{Color.blue, Color.green, Color.red, Color.yellow, Color.white, Color.cyan};

    public static Color getColorForPlayer(Player player) {
        return colors[player.getID()];
    }
}
