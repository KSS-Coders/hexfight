package org.cjforge.hexed.context.config;

import org.apache.log4j.Logger;
import org.cjforge.hexed.context.Context;
import org.newdawn.slick.gui.GUIContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class ConfigContainer {

    private Logger l = Logger.getLogger(this.getClass());


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

    public String getTheme() {
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

    public Object getProperty(String ref) {
        Object o = properties.get(ref);
        if (o == null) {
            o = ConfigDefaults.get(ref);
            setProperty(ref, o);
        }
        return o;
    }

    public int getInt(String ref) {
        Object prop = getProperty(ref);
        if (prop instanceof String) {
            try {
                int i = Integer.parseInt((String) prop);
                setProperty(ref, i);
                return i;
            } catch (Exception e) {
                l.info("Using default value for " + ref);
                int i = (int) ConfigDefaults.get(ref);
                setProperty(ref, i);
                return i;
            }
        } else {
            return (int) prop;
        }
    }

    public double getDobule(String ref) {
        Object prop = getProperty(ref);
        if (prop instanceof String) {
            try {
                double i = Double.parseDouble((String) prop);
                setProperty(ref, i);
                return i;
            } catch (Exception e) {
                l.info("Using default value for " + ref);
                double i = (double) ConfigDefaults.get(ref);
                setProperty(ref, i);
                return i;
            }
        } else {
            return (double) prop;
        }
    }

    public int getTileSize() {
        return getInt(ConfigKey.TILE_SIZE_KEY);
    }

    public int getTilesNumber() {
        return getInt(ConfigKey.TILES_NUMBER_KEY);
    }

    public int getInitialPlayerForces() {
        return getInt(ConfigKey.INITIAL_PLAYER_FORCES_KEY);
    }

    public int getInitialNeutralForces() {
        return getInt(ConfigKey.INITIAL_NEUTRAL_FORCES_KEY);
    }

    public int getPlayersNumber() {
        return getInt(ConfigKey.PLAYERS_NUMBER_KEY);
    }

    public double getNonEmptyTilesFactor() {
        return getDobule(ConfigKey.NON_EMPTY_TILES_FACTOR_KEY);
    }

    public void setProperty(String ref, Object obj) {
        properties.put(ref, obj);
    }
}
