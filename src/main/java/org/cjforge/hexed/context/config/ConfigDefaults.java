package org.cjforge.hexed.context.config;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class ConfigDefaults {
    private static final Map<String, Object> defaults = new HashMap<>();
    static{
        defaults.put(ConfigKey.THEME_KEY, "space");
        defaults.put(ConfigKey.RESOLUTION_KEY, new Dimension(800, 600));
        defaults.put(ConfigKey.TILE_SIZE_KEY, 100);
        defaults.put(ConfigKey.TILES_NUMBER_KEY, 6);
        defaults.put(ConfigKey.INITIAL_PLAYER_FORCES_KEY, 50);
        defaults.put(ConfigKey.INITIAL_NEUTRAL_FORCES_KEY, 20);
        defaults.put(ConfigKey.NON_EMPTY_TILES_FACTOR_KEY, 0.1);
        defaults.put(ConfigKey.PLAYERS_NUMBER_KEY, 6);
    }

    public static Object get(String ref){
        return defaults.get(ref);
    }
}
