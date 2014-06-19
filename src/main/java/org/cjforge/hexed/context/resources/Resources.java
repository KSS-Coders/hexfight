package org.cjforge.hexed.context.resources;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class Resources {

    public static final Resources instance = new Resources();

    public final ImagesManager images;
    public final FontsManager fonts;

    private Resources() {
        fonts = new FontsManager();
        images = new ImagesManager();
    }

    public static Resources get() {
        return instance;
    }

}
