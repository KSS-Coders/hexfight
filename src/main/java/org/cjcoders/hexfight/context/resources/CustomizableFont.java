package org.cjcoders.hexfight.context.resources;

import org.newdawn.slick.*;
import org.newdawn.slick.Font;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class CustomizableFont {

    public static final int PLAIN = java.awt.Font.PLAIN;
    public static final int BOLD = java.awt.Font.BOLD;
    public static final int ITALIC = java.awt.Font.ITALIC;

    private final java.awt.Font font;

    public CustomizableFont(java.awt.Font font) {
        this.font = font;
    }

    public CustomizableFont withSize(float size){
        return new CustomizableFont(font.deriveFont(size));
    }

    public CustomizableFont withStyle(int style){
        return new CustomizableFont(font.deriveFont(style));
    }

    public Font get(){ return new TrueTypeFont(font, false); }

    public Font getWithSize(float size){
        return new TrueTypeFont(font.deriveFont(size), false);
    }
}
