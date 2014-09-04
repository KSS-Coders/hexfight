package org.cjforge.hexed.utils;

import org.newdawn.slick.Color;

/**
 * Created by mrakr_000 on 2014-09-04.
 */
public class SlickColors {

    private static final float MINIMAL_CHANEL_VALUE = 0.25f;

    private SlickColors(){}

    public static Color ensurePositiveChanels(Color color) {
        return new Color(
                ensurePositive(color.r, MINIMAL_CHANEL_VALUE),
                ensurePositive(color.g, MINIMAL_CHANEL_VALUE),
                ensurePositive(color.b, MINIMAL_CHANEL_VALUE)
        );
    }

    private static float ensurePositive(float i, float def) {
        if(i <= 0) return def;
        else return i;
    }
}
