package org.cjforge.hexed.context.resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public class ImagesManager extends ResourceManager<Image>{

    public void load(String ref, String path) throws IOException, SlickException {
        if(refExists(ref)) return;
        InputStream inputStream	= ResourceLoader.getResourceAsStream(path);
        addResource(ref, new Image(inputStream, ref, false));
    }

}
