package org.cjcoders.hexfight.gui.utils.resources;

import org.cjcoders.hexfight.gui.utils.fonts.CustomizableFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class FontsManager {
    private Map<String, Font> fonts;

    FontsManager() {
        this.fonts = new HashMap<>();
    }

    public void load(String name, String path) throws IOException, FontFormatException {
        if(fonts.keySet().contains(name)) return;
        InputStream inputStream	= ResourceLoader.getResourceAsStream(path);
        fonts.put(name, Font.createFont(Font.TRUETYPE_FONT, inputStream));
    }

    public CustomizableFont get(String name){
        return new CustomizableFont(fonts.get(name));
    }
}
