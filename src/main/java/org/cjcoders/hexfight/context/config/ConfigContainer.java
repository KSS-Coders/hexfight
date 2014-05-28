package org.cjcoders.hexfight.context.config;

import org.apache.log4j.Logger;
import org.cjcoders.hexfight.context.Context;
import org.newdawn.slick.gui.GUIContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class ConfigContainer {

    private Logger l = Logger.getLogger(this.getClass());

    private static final int DEFAULT_TILE_SIZE = 100;
    public static final String TILE_SIZE_KEY = "tileSize";

    private Map<String, Object> properties;

    public ConfigContainer() {
        properties = new HashMap<>();
    }

    public Dimension getScreenResolution() {
        return (Dimension) getProperty(ConfigKey.RESOLUTION_KEY);
    }
    public void setScreenResolution(GUIContext container) {
        Dimension newRes = readScreenResolution(container);
        setProperty(ConfigKey.RESOLUTION_KEY, newRes);
    }

    public String getTheme(){
        return (String) getProperty(ConfigKey.THEME_KEY);
    }

    private Dimension readScreenResolution(GUIContext container) {
        return new Dimension(container.getScreenWidth(), container.getScreenHeight());
    }

    public void init(Context context, GUIContext container) {
        setScreenResolution(container);
    }

    public void addAll(Map<String, Object> config) {
        properties.putAll(config);
    }

    public Object getProperty(String ref){
        Object o = properties.get(ref);
        if(o == null) o = ConfigDefaults.get(ref);
        return o;
    }
    public int getTileSize(){
        Object prop = getProperty(TILE_SIZE_KEY);
        try {
            return Integer.parseInt((String) prop);
        }catch(NullPointerException npe){
            l.info("Using default value for " + TILE_SIZE_KEY);
            return DEFAULT_TILE_SIZE;
        }catch(NumberFormatException nfe){
            l.warn("Property " + TILE_SIZE_KEY + " has wrong format. Returning default value.");
            return DEFAULT_TILE_SIZE;
        }
    }
    public void setProperty(String ref, Object obj){
        properties.put(ref, obj);
    }
}
