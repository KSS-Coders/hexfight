package org.cjcoders.hexfight.context.resources;

import org.apache.log4j.Logger;
import org.newdawn.slick.Font;
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

    private Logger l = Logger.getLogger(this.getClass().getName());

    private Map<String, CustomizableFont> fonts;
    private Map<FontSpec, Font> loadedFonts;

    FontsManager() {
        this.fonts = new HashMap<>();
        this.loadedFonts = new HashMap<>();
    }

    public void load(String name, String path) throws IOException, FontFormatException {
        if(fonts.keySet().contains(name)) return;
        InputStream inputStream	= ResourceLoader.getResourceAsStream(path);
        fonts.put(name, new CustomizableFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream)));
    }

    public Font get(String ref, int size, String options) {
        FontSpec spec = new FontSpec(ref, size, options);
        if(loadedFonts.containsKey(spec)){
            l.trace("Requested font " + spec + ". Loaded.");
            return loadedFonts.get(spec);
        }
        l.trace("Requested font " + spec + ". Loading.");
        CustomizableFont cFont = fonts.get(ref);
        cFont = cFont.withSize(size);
        if(spec.isBold()) cFont.withStyle(CustomizableFont.BOLD);
        if(spec.isItalic()) cFont.withStyle(CustomizableFont.ITALIC);
        Font f = cFont.get();
        loadedFonts.put(spec, f);
        return f;
    }

    private class FontSpec {
        final String ref;
        final int size;
        final String options;

        private FontSpec(String ref, int size, String options) {
            this.ref = ref;
            this.size = size;
            this.options = options;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof FontSpec){
                FontSpec fs = (FontSpec) obj;
                return fs.ref.equals(ref) && fs.size == size && equalOptions(fs);
            }
            return false;
        }

        private boolean equalOptions(FontSpec fs) {
            return isUnderscored() == fs.isUnderscored() &&
                    isBold() == fs.isBold() &&
                    isItalic() == fs.isItalic();
        }

        @Override
        public String toString() {
            return "[" + ref + ", " + size + ", " + options + "]";
        }

        private boolean isUnderscored() {
            return hasOption("u") || hasOption("U");
        }

        private boolean hasOption(CharSequence c){
            return options.contains(c);
        }

        private boolean isBold() {
            return hasOption("b") || hasOption("B");
        }

        private boolean isItalic(){
            return hasOption("i") || hasOption("I");
        }
    }
}

