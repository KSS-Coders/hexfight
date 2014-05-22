package org.cjcoders.hexfight.context.config;

import org.cjcoders.hexfight.context.Context;
import org.newdawn.slick.gui.GUIContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class ConfigContainer {

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
    public void setProperty(String ref, Object obj){
        properties.put(ref, obj);
    }
}
