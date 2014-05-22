package org.cjcoders.hexfight.context.config;

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
    }

    public static Object get(String ref){
        return defaults.get(ref);
    }
}
