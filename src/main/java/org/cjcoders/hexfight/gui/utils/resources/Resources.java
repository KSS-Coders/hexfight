package org.cjcoders.hexfight.gui.utils.resources;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class Resources {

    public static final Resources instance = new Resources();

    public final ImagesManager images;
    public final FontsManager fonts;

    public static Resources get(){ return instance; }

    private Resources() {
        fonts = new FontsManager();
        images = new ImagesManager();
    }

}
